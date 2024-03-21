package com.example.MovieTonight.controllers;

import com.example.MovieTonight.algorithm.*;
import com.example.MovieTonight.services.QuestionAlgorithmImp;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("mt")
public class QuestionAlgorithmController {
    private final QuestionAlgorithmImp questionAlgorithmImp;

    public QuestionAlgorithmController(QuestionAlgorithmImp questionAlgorithmImp) {
        this.questionAlgorithmImp = questionAlgorithmImp;
    }

    @GetMapping("getStartingQuestions")
    public StartingQuestionsResponse getStartingQuestions(){
        return this.questionAlgorithmImp.getStartingQuestions();
    }

    @GetMapping("getGenreQuestions")
    public GenreQuestionsResponse getGenreQuestions(GenreQuestionsRequest genreQuestionsRequest){
        return this.questionAlgorithmImp.getGenreQuestions(genreQuestionsRequest);
    }

    @GetMapping("getKeywordQuestions")
    public KeywordQuestionsResponse getKeywordQuestions(KeywordQuestionsRequest keywordQuestionsRequest){
        return this.questionAlgorithmImp.getKeywordQuestions(keywordQuestionsRequest);
    }

    @GetMapping("getMovieRecommendations")
    public List<UserMovie> getMovieRecommendations(MovieRecommendationRequest movieRecommendationRequest){
        return this.questionAlgorithmImp.getMovieRecommendations(movieRecommendationRequest);
    }
}
