package com.example.MovieTonight.services;

import com.example.MovieTonight.algorithm.UserKeywords;
import com.example.MovieTonight.algorithm.UserMovie;

import java.util.List;

public interface AlgorithmService {
    UserKeywords fetchKeywords(); //zwraca keywordy dla gatunkow
    List<Object> getRecommendation(); //filmy w formie List<Object>
    List<UserMovie> getRecommendationV2(); //filmy w formie  List<UserMovie>, o  okolo 20ms wolniejsza, ale mamy liste filmow jako klase (avg 70ms)

}
