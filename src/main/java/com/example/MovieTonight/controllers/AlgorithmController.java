package com.example.MovieTonight.controllers;

import com.example.MovieTonight.algorithm.UserGenre;
import com.example.MovieTonight.algorithm.UserKeywords;
import com.example.MovieTonight.algorithm.UserMovie;
import com.example.MovieTonight.services.AlgorithmServiceImp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("mt")
public class AlgorithmController {

    private final AlgorithmServiceImp algorithmServiceImp;

    public AlgorithmController(AlgorithmServiceImp algorithmServiceImp) {
        this.algorithmServiceImp = algorithmServiceImp;
    }
    @GetMapping("/getUserGenres")
    public UserGenre getWantedGenres(){
        return algorithmServiceImp.getUserGenres();
    }
    @GetMapping("/fetchKeywords")
    public UserKeywords fetchKeywords(){
        return algorithmServiceImp.fetchKeywords();
    }
    @GetMapping("/getRecommendation")
    public List<Object> getRecommendation(){
        return algorithmServiceImp.getRecommendation();
    }
    @GetMapping("/getRecommendationV2")
    public List<UserMovie> getRecommendation2(){
        return algorithmServiceImp.getRecommendationV2();
    }

}
