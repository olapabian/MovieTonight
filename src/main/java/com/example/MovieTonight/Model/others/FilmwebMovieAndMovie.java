package com.example.MovieTonight.Model.others;
import com.example.MovieTonight.Model.database.FilmwebMovie;
import com.example.MovieTonight.Model.database.Movie;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilmwebMovieAndMovie {
    private FilmwebMovie filmwebMovie;
    private Movie movie;
}
