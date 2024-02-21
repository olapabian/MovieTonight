package com.example.MovieTonight.algorithm;

import com.example.MovieTonight.mappers.Mapper;
import com.example.MovieTonight.model.database.GenresInfo;
import com.example.MovieTonight.model.database.KeywordPoints;
import com.example.MovieTonight.model.database.KeywordsInfo;
import com.example.MovieTonight.repository.GenresInfoRepository;
import com.example.MovieTonight.repository.KeywordPointsRepository;
import com.example.MovieTonight.repository.KeywordsInfoRepository;
import com.example.MovieTonight.repository.MoviePointsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class QuestionService {
    KeywordsInfoRepository keywordsInfoRepository;
    GenresInfoRepository genresInfoRepository;
    MoviePointsRepository moviePointsRepository;
    KeywordPointsRepository keywordPointsRepository;
    Mapper mapper;

    public void stimulateAlgorithm(){ //stumulatoalogrytmoinator
        /*
        Zdajemy sobie sprawe ze całosc jest delikatnie mowiac do uproszczenia
        Zalozmy ze uzytkownik ma ochote na film z ciemnej strony mocy
        */
        List<GenresInfo> genres = new ArrayList<>();
        List<KeywordPoints> keywords = new ArrayList<>();
        FalseUser falseUser = new FalseUser(genresInfoRepository.findAll());

        genres.add(genresInfoRepository.findById(18L).get());//dramat
        genres.add(genresInfoRepository.findById(53L).get());//triler
        genres.add(genresInfoRepository.findById(80L).get());//kryminal
        genres.add(genresInfoRepository.findById(27L).get());//horror


        for(GenresInfo g: genres){  // wybieram losowe kewywordy z 50 najpopularnieszych
            falseUser.IncreasePoint(g,GlobalVariables.genre);
            List<Long> list = keywordPointsRepository.get50MostAccurateKeywordsForGenre(g);
            List<Long> randomKeywords = getRandomElements(list, 2);

            for(Long keywordId : randomKeywords){
                List<KeywordPoints> keywordPoints = keywordPointsRepository.GetGenrePointsForKeyword(keywordId);
                List<GenrePointsForKeyword> genrePointsForKeywords = mapper.mapFromKeywordPointsToGenrePointsForKeyword(keywordPoints);

                for(GenrePointsForKeyword gpfk: genrePointsForKeywords){ //trzecia petla jest wesolo
                        GenresInfo gi = genresInfoRepository.findById(gpfk.genre_id).get();
                        falseUser.IncreasePoint(gi,gpfk.points);
                }
            }
        }
        System.out.println(falseUser);

    }

    public static <T> List<T> getRandomElements(List<T> list, int k) {
        Random rand = new Random();
        int n = list.size();
        for (int i = n - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            T temp = list.get(i);
            list.set(i, list.get(j));
            list.set(j, temp);
        }
        return list.subList(0, k);
    }
}
