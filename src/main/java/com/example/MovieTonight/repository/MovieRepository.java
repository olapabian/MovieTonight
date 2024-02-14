package com.example.MovieTonight.repository;

import com.example.MovieTonight.model.database.FilmwebMovie;
import com.example.MovieTonight.model.database.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findByFilmweb(FilmwebMovie filmwebMovie);

}
