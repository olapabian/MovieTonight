package com.example.MovieTonight.controllers;


import com.example.MovieTonight.dataFromApi.MoviePointsStructure;
import com.example.MovieTonight.model.database.MoviePoints;
import com.example.MovieTonight.model.database.TmdbMovie;
import com.example.MovieTonight.repository.*;
import com.example.MovieTonight.services.implementations.MoviePointsServiceImp;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("mt")
public class MoviePointsController {

    private final MoviePointsServiceImp moviePointsServiceImp;
    private final MovieGenresRepository movieGenresRepository;

    public MoviePointsController(MoviePointsServiceImp moviePointsServiceImp, MovieGenresRepository movieGenresRepository) {
        this.moviePointsServiceImp = moviePointsServiceImp;
        this.movieGenresRepository = movieGenresRepository;
    }


    @GetMapping("/getGenrePointsForMovieFromKeywords")
    public Long getGenrePointsForMovieFromKeywords(@RequestParam TmdbMovie tmdbId, @RequestParam Long genreId){

        return moviePointsServiceImp.genrePointsForMovieFromKeywords(tmdbId, genreId);

    }

    @PostMapping("/allMoviePoints")
    public void allMoviePoints(){

        moviePointsServiceImp.allMoviePoints();

    }

    @GetMapping("/getMoviesFromQuiz")
    public List<Long> getMoviesFromQuiz(){
        List<Long> goodLista = new ArrayList<>();
        goodLista.add(18L);
        List<Long> badLista = new ArrayList<>();
        badLista.add(53L);

        return movieGenresRepository.moviesFromQuiz(goodLista, badLista);

    }

}
