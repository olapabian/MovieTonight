package com.example.MovieTonight.repository;

import com.example.MovieTonight.model.database.TmdbMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TmdbMovieRepository extends JpaRepository<TmdbMovie,Long> {
}
