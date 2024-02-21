package com.example.MovieTonight.repository;

import com.example.MovieTonight.algorithm.GenrePointsForKeyword;
import com.example.MovieTonight.model.database.GenresInfo;
import com.example.MovieTonight.model.database.KeywordPoints;
import com.example.MovieTonight.model.database.MovieDirector;
import com.example.MovieTonight.model.database.MovieGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeywordPointsRepository extends JpaRepository<KeywordPoints,Long> {
    @Query("SELECT ki.keywordId.id FROM KeywordPoints ki WHERE ki.genreId=?1 order by ki.points DESC LIMIT 50")
    List<Long> get50MostAccurateKeywordsForGenre(GenresInfo genre);

    @Query("SELECT ki FROM KeywordPoints ki WHERE ki.keywordId.id=?1")
    List<KeywordPoints> GetGenrePointsForKeyword (Long keywordId);
}

