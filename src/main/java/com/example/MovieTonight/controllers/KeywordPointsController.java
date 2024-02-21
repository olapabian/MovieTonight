package com.example.MovieTonight.controllers;

import com.example.MovieTonight.dataFromApi.KeywordCount;
import com.example.MovieTonight.services.implementations.KeywordPointsServiceImp;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("mt")
public class KeywordPointsController {

    private final KeywordPointsServiceImp keywordPointsServiceImp;

    public KeywordPointsController(KeywordPointsServiceImp keywordPointsServiceImp) {
        this.keywordPointsServiceImp = keywordPointsServiceImp;
    }

    @GetMapping("/getGenres")
    public List<KeywordCount> getkeyword(@RequestParam long keywordId){

        return keywordPointsServiceImp.getGenres(keywordId);

    }

    @PostMapping("/countGenresforKeyword")
    public String getCountedGenresForKeywords(){

        try{
            keywordPointsServiceImp.countGenresPointsForKeywords();
        } catch (Exception e){
            e.printStackTrace();
            return "Error has occured";
        }

        return "Zapisano punkty keywordow";
    }

    @GetMapping("/dupa")
    public List<KeywordCount> getMoviesFromQuiz(){
        return keywordPointsServiceImp.getGenres(18L);

    }
}
