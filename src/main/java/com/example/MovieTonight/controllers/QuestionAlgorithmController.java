package com.example.MovieTonight.controllers;

import com.example.MovieTonight.algorithm.QuestionForQuiz;
import com.example.MovieTonight.services.QuestionAlgorithmImp;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("mt")
public class QuestionAlgorithmController {
    private final QuestionAlgorithmImp questionAlgorithmImp;

    public QuestionAlgorithmController(QuestionAlgorithmImp questionAlgorithmImp) {
        this.questionAlgorithmImp = questionAlgorithmImp;
    }

    @GetMapping("getStartingQuestions")
    public List<QuestionForQuiz> getStartingQuestions(){
        return this.questionAlgorithmImp.getStartingQuestions();
    }

    @GetMapping("getGenreQuestions")
    public List<QuestionForQuiz> getGenreQuestions(String quizId, String userId, String side, String time, String relaseDate){
        return this.questionAlgorithmImp.getGenreQuestions(Integer.parseInt(quizId), Integer.parseInt(userId), side, time, relaseDate);
    }

    @GetMapping("getKeywordQuestions")
    public List<QuestionForQuiz> getKeywordQuestions(@RequestParam String quizId,
                                                    @RequestParam String userId,
                                                    @RequestParam List<String> wantedGenres,
                                                    @RequestParam List<String> unwantedGenres){
        return this.questionAlgorithmImp.getKeywordQuestions(Integer.parseInt(quizId), Integer.parseInt(userId), wantedGenres, unwantedGenres);
    }
}
