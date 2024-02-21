package com.example.MovieTonight.services.interfaces;

import com.example.MovieTonight.model.database.TmdbMovie;

public interface MoviePointsService {

    Long genrePointsForMovieFromKeywords(TmdbMovie tmdbId, Long genreId);

    void allMoviePoints();
}
