package com.example.MovieTonight.repository;

import com.example.MovieTonight.model.database.KeywordPoints;
import com.example.MovieTonight.model.database.MoviePoints;
import com.example.MovieTonight.model.database.TmdbMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MoviePointsRepository extends JpaRepository<MoviePoints,Long> {
    @Query("SELECT kp.points FROM MovieKeyword AS tm JOIN KeywordPoints AS kp ON tm.keyword.id = kp.keywordId WHERE tm.tmdb=?1 AND kp.genreId=?2")
    List<Long> genrePointsForMovie(TmdbMovie tmdbId, Long genreId);
}
