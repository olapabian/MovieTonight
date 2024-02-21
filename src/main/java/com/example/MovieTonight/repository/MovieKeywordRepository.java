package com.example.MovieTonight.repository;

import com.example.MovieTonight.model.database.MovieKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieKeywordRepository extends JpaRepository<MovieKeyword, Long> {

    @Query("SELECT m.keyword.id, g.genre.id FROM MovieKeyword m JOIN MovieGenre g ON m.tmdb = g.tmdbMovie WHERE m.keyword.id =?1 ")
    List<Object[]>  countKeywordsPerEntity(long keywordID);
}