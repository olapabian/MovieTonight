package com.example.MovieTonight.Repository;

import com.example.MovieTonight.Model.FilmwebMovie;
import com.example.MovieTonight.Model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmwebMovieRepository extends JpaRepository<FilmwebMovie,Long> {
}
