package com.example.MovieTonight.services;

import com.example.MovieTonight.algorithm.UserGenre;
import com.example.MovieTonight.algorithm.UserKeywords;
import com.example.MovieTonight.algorithm.UserMovie;
import com.example.MovieTonight.repository.AlgorithmRepository;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Getter
public class AlgorithmServiceImp implements AlgorithmService{
   /*
    Nie jestem pewien czy o takie ustawianie na sztywno chodziło wiec zrobilem zeby keywordy sie same wypełniały na podstawie gatunków, jesli chcemy je
    ustawic recznie w tym programie to tez jest taka mozliosc wystarczy je wpisac na sztywno i usunac fragment ktory je uzupelnia
    klasy dodane w folderze algorithm są po to aby zmimikowac chociaz lekko zapis ktory jest na miro stworzony przez Pawla zwracach jsonow, ja sam
    tutaj nic nie zapisuje do bazy
    */

    public UserGenre userGenres = new UserGenre(List.of("Przygodowy", "Fantasy"),
            List.of("Sci-Fi", "Thriller"));
    public UserKeywords userKeywords = new UserKeywords();

    AlgorithmRepository algorithmRepository;

    public AlgorithmServiceImp(AlgorithmRepository algorithmRepository) {
        this.algorithmRepository = algorithmRepository;
    }

    public UserKeywords fetchKeywords(){
        //warto tutaj napisac ze nie zwracam ilosci występowań keyworda bo jest mi ona zbędna, wystarczą same nazwy
        List<String> keywords = algorithmRepository.mostImportantKeywordsForGenre(userGenres.positive,userGenres.negative);

        //wypelnianie kewordow
        List<String> wantedKeywords = keywords.stream().limit(10).collect(Collectors.toList());  //pierwszych 10

        List<String> unwantedKeywords = keywords.stream().skip(10).collect(Collectors.toList());   //jakies 2 ktorych nie chcemy
        Collections.shuffle(unwantedKeywords);
        unwantedKeywords = unwantedKeywords.stream().limit(2).collect(Collectors.toList());

        userKeywords.setPositive(wantedKeywords);
        userKeywords.setNegative(unwantedKeywords);

        return userKeywords;

    }

    public List<Object> getRecommendation(){
        fetchKeywords();
        return algorithmRepository.getRecommendation(userGenres.positive,userGenres.negative,
                userKeywords.positive,  userKeywords.negative);
    }
    public List<UserMovie> getRecommendationV2() {
        fetchKeywords();
        List<Object> sourceList = algorithmRepository.getRecommendation(userGenres.positive,userGenres.negative,
                userKeywords.positive, userKeywords.negative);
        List<UserMovie> userMovies = new ArrayList<>();

        for (Object sourceObject : sourceList) {
            if (sourceObject instanceof Object[]) {
                Object[] movieArray = (Object[]) sourceObject;
                    try {
                        UserMovie userMovie = new UserMovie();
                        userMovie.setTitle((String) movieArray[0]);
                        userMovie.setOriginalTitle((String) movieArray[1]);
                        userMovie.setMatchingKeywords((Long) movieArray[2]);
                        userMovies.add(userMovie);
                    } catch (ClassCastException | NullPointerException e) {
                        System.err.println("Błąd podczas przetwarzania tablicy: " + e.getMessage());
                    }
            } else {
                System.err.println("Nieprawidłowy typ elementu");
            }
        }

        return userMovies;
    }
}
