package com.example.MovieTonight.Model.others;


import com.example.MovieTonight.Model.database.Movie;
import com.example.MovieTonight.Model.database.TmdbMovie;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TmdbMovieAndMovie {
    TmdbMovie tmdbMovie;
    Movie movie;
}
