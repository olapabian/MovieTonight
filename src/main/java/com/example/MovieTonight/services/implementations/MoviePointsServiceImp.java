package com.example.MovieTonight.services.implementations;

import com.example.MovieTonight.dataFromApi.MoviePointsStructure;
import com.example.MovieTonight.model.database.MoviePoints;
import com.example.MovieTonight.model.database.TmdbMovie;
import com.example.MovieTonight.repository.*;
import com.example.MovieTonight.services.interfaces.MoviePointsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@AllArgsConstructor
public class MoviePointsServiceImp implements MoviePointsService {
    TmdbMovieRepository tmdbMovieRepository;
    MovieKeywordRepository movieKeywordRepository;
    KeywordPointsRepository keywordPointsRepository;
    MoviePointsRepository moviePointsRepository;
    GenresInfoRepository genresInfoRepository;
    MovieGenresRepository movieGenresRepository;

    public Long genrePointsForMovieFromKeywords(TmdbMovie tmdbId, Long genreId) {

        List<Long> genrePoints = moviePointsRepository.genrePointsForMovie(tmdbId, genreId);
        long sum = genrePoints.stream().mapToLong(Long::longValue).sum();

        return sum;

    }

    public void allMoviePoints(){

        List<Long> tmdbId = tmdbMovieRepository.allTmdbId();
        List<MoviePoints> moviePoints = new ArrayList<>();
        List<Long> genresId = genresInfoRepository.allGenresId();

        for(Long movieId : tmdbId){
            TmdbMovie test = tmdbMovieRepository.findById(movieId).get();
            List<Long> movieGenres = movieGenresRepository.allMovieGenresId(test.getId());

            for(Long genreId : genresId){
                Long points = this.genrePointsForMovieFromKeywords(test, genreId);
                if(movieGenres.contains(genreId)){
                    points += 5000;
                }
                MoviePoints movie = new MoviePoints();
                movie.setTmdbId(movieId);
                movie.setGenreId(genreId);
                movie.setPoints(points);
                moviePoints.add(movie);
            }
        }

        moviePointsRepository.saveAll(moviePoints);

    }
}
