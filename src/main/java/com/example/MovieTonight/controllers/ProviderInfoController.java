package com.example.MovieTonight.controllers;

import com.example.MovieTonight.dataFromApi.KeywordCount;
import com.example.MovieTonight.model.database.KeywordPoints;
import com.example.MovieTonight.model.database.MovieGenre;
import com.example.MovieTonight.model.database.ProvidersInfo;
import com.example.MovieTonight.services.implementations.ProviderInfoServiceImp;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("mt")
public class ProviderInfoController {

    private final ProviderInfoServiceImp providerInfoServiceImp;

    public ProviderInfoController(ProviderInfoServiceImp providerInfoServiceImp) {
        this.providerInfoServiceImp = providerInfoServiceImp;
    }
    @GetMapping("/getAll")
    public String getAll(){

        List<String> namesAndIds = new ArrayList<>();
        List<ProvidersInfo> providersInfos =providerInfoServiceImp.getAllProviders();
        for (ProvidersInfo provider : providersInfos) {
            namesAndIds.add(provider.getId() + ": " + provider.getName());
        }
        namesAndIds.forEach(System.out::println);

        return "BajoJajo";
    }

    @GetMapping("/getMovieGenre")
    public List<MovieGenre> getMovieGenre(){

       return providerInfoServiceImp.getAllTmdbMovies();

    }
    @GetMapping("/getGenres")
    public List<KeywordCount> getkeyword(@RequestParam long keywordId){

        return providerInfoServiceImp.getGenres(keywordId);

    }

    @PostMapping("/countGenresforKeyword")
    public String getCountedGenresForKeywords(){

        try{
            providerInfoServiceImp.countGenresOccurrenceForKeywords();
        } catch (Exception e){
            e.printStackTrace();
            return "Error has occured";
        }

        return "Zapisano punkty keywordow";
    }
}
