package com.example.MovieTonight.repository;

import com.example.MovieTonight.model.FilmwebMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmwebMovieRepository extends JpaRepository<FilmwebMovie,Long> {

}
