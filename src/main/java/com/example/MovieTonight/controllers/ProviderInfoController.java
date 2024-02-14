package com.example.MovieTonight.controllers;

import com.example.MovieTonight.dataFromApi.KeywordCount;
import com.example.MovieTonight.dataFromApi.KeywordStatistic;
import com.example.MovieTonight.model.database.MovieGenre;
import com.example.MovieTonight.model.database.ProvidersInfo;
import com.example.MovieTonight.model.database.TmdbMovie;
import com.example.MovieTonight.repository.MovieGenresRepository;
import com.example.MovieTonight.repository.ProvidersInfoRepository;
import com.example.MovieTonight.repository.TmdbMovieRepository;
import com.example.MovieTonight.services.implementations.ProviderInfoServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
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

    @GetMapping("/countGenresforKeyword")
    public HashMap<Long, List<KeywordStatistic>> getCountedGenresForKeywords(){

        return providerInfoServiceImp.countGenresOccurrenceForKeywords();

    }
}
