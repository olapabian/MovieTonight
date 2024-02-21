package com.example.MovieTonight.services.implementations;

import com.example.MovieTonight.dataFromApi.KeywordCount;
import com.example.MovieTonight.model.database.GenresInfo;
import com.example.MovieTonight.model.database.KeywordPoints;
import com.example.MovieTonight.dataFromApi.KeywordStatistic;
import com.example.MovieTonight.mappers.KeywordMapper;
import com.example.MovieTonight.model.database.KeywordsInfo;
import com.example.MovieTonight.model.database.MovieGenre;
import com.example.MovieTonight.repository.*;
import com.example.MovieTonight.services.interfaces.KeywordPointsSerivce;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class KeywordPointsServiceImp implements KeywordPointsSerivce {

    ProvidersInfoRepository providersInfoRepository;
    TmdbMovieRepository tmdbMovieRepository;
    MovieGenresRepository movieGenresRepository;
    MovieKeywordRepository movieKeywordRepository;
    KeywordsInfoRepository keywordsInfoRepository;
    KeywordPointsRepository keywordPointsRepository;
    GenresInfoRepository genresInfoRepository;
    KeywordMapper keywordMapper;

    public List<KeywordCount> getGenres(long keywordId) {

        List<Object[]> queryResults = movieKeywordRepository.countKeywordsPerEntity(keywordId);
        List<KeywordCount> keywordCounts = new ArrayList<>();

        for (Object[] result : queryResults) {
            KeywordCount keywordCount = keywordMapper.mapFromObjectToKeywordCount(result);
            keywordCounts.add(keywordCount);
        }


        return keywordCounts;
    }

    public void countGenresPointsForKeywords() {
        List<KeywordsInfo> list = keywordsInfoRepository.findAll();
        List<Long> keywordIds = new ArrayList<>();
        for(KeywordsInfo keywordsInfo: list){
            keywordIds.add(keywordsInfo.getId());
        }

        List<KeywordPoints> resultList = new ArrayList<>();

        for (Long keywordId : keywordIds) {
            List<KeywordStatistic> keywordStatistic = new ArrayList<>();
            List<KeywordCount> keywordCounts = getGenres(keywordId);

            for (KeywordCount kcount : keywordCounts) {
                boolean found = false;
                for (KeywordStatistic ks : keywordStatistic) {
                    if (ks.getGenreId() == kcount.getGenreId()) {
                        ks.increaseOccurrences();
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    KeywordStatistic newStatistic = new KeywordStatistic();
                    newStatistic.setGenreId(kcount.getGenreId());
                    newStatistic.setOccurrences(1);
                    keywordStatistic.add(newStatistic);

                }
            }
            for(KeywordStatistic kS: keywordStatistic){

                KeywordPoints kp = new KeywordPoints();
                kp.setKeywordId( keywordsInfoRepository.findById(keywordId).get());
                kp.setGenreId(genresInfoRepository.findById(kS.getGenreId()).get());

                kp.setPoints(kS.getOccurrences());
                resultList.add(kp);
            }
        }
        keywordPointsRepository.saveAll(resultList);

    }

}