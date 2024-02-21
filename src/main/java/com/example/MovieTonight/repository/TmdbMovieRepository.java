package com.example.MovieTonight.repository;

import com.example.MovieTonight.model.database.TmdbMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TmdbMovieRepository extends JpaRepository<TmdbMovie,Long> {
    @Query("SELECT id FROM TmdbMovie")
    List<Long> allTmdbId();
}
