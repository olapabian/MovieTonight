package com.example.MovieTonight.repository;

import com.example.MovieTonight.model.database.MovieGenre;
import com.example.MovieTonight.model.database.TmdbMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieGenresRepository extends JpaRepository<MovieGenre,Long> {

    @Query("SELECT mg FROM MovieGenre mg WHERE mg.tmdbMovie.id= ?1")
    List<MovieGenre> findMovieGenreByTmdbId(long tmdbId);
}
