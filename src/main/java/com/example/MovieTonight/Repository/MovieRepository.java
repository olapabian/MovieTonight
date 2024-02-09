package com.example.MovieTonight.Repository;

import com.example.MovieTonight.Model.FilmwebMovie;
import com.example.MovieTonight.Model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findByFilmweb(FilmwebMovie filmwebMovie);
}
