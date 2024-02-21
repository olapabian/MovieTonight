package com.example.MovieTonight.repository;

import com.example.MovieTonight.model.database.GenresInfo;
import com.example.MovieTonight.model.database.TmdbMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenresInfoRepository extends JpaRepository<GenresInfo,Long> {
    @Query("SELECT kp.points FROM MovieKeyword AS tm JOIN KeywordPoints AS kp ON tm.keyword.id = kp.keywordId.id WHERE tm.tmdb=?1 AND kp.genreId.id=?2")
    List<Long> genrePointsForMovie(TmdbMovie tmdbId, Long genreId);

    @Query("SELECT id FROM GenresInfo")
    List<Long> allGenresId();
}
