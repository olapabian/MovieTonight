package com.example.MovieTonight.repository;

import com.example.MovieTonight.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlgorithmRepository extends JpaRepository<Movie,Long> {
    @Query(nativeQuery = true, value = "select ki.name from movie_keywords mk join keywords_info ki on mk.keyword_id=ki.keyword_id" +
            " join (SELECT m.tmdb_id, m.title, m.original_title, count (distinct gi.name)" +
            " FROM movie m INNER JOIN movie_genres mg on mg. tmdb_id = m. tmdb_id inner join" +
            " genres_info gi on gi.genre_id = mg. genre_id where gi.name In (:wantedGenres)" +
            " and gi.name not in (:unwantedGenres)" +
            " group by m. tmdb_id, m.title, m.original_title having count (distinct gi.name) > 1) as dramaty_historyczne" +
            " on mk. tmdb_id = dramaty_historyczne.tmdb_id group by ki.name order by count(*) desc")
    List<String> mostImportantKeywordsForGenre(@Param("wantedGenres") List<String> wantedGenres, @Param("unwantedGenres") List<String> unwantedGenres);


    @Query(nativeQuery = true, value = "select filmy.title, filmy.original_title, count(distinct ki.name) as zgodne_keywordy" +
            " from movie_keywords mk join keywords_info ki on mk.keyword_id=ki.keyword_id" +
            " join ( SELECT m.tmdb_id, m.title, m.original_title, count(distinct gi.name) as zgodne_gatunki" +
            " FROM movie m INNER JOIN movie_genres mg on mg.tmdb_id = m.tmdb_id inner join genres_info gi on gi.genre_id = mg.genre_id" +
            " where gi.name IN (:wantedGenres) and gi.name not IN (:unwantedGenres)" +
            " group by m.tmdb_id, m.title, m.original_title having count(distinct gi.name) > 1) filmy on mk.tmdb_id = filmy.tmdb_id" +
            " where ki.name IN (:wantedKeywords)" +
            " and ki.name NOT In(:unwantedKeywords)" +
            " group by filmy.tmdb_id, filmy.title, filmy.original_title" +
            " order by zgodne_keywordy desc, title asc;")
    List<Object> getRecommendation(@Param("wantedGenres") List<String> wantedGenres,
                                     @Param("unwantedGenres") List<String> unwantedGenres,
                                     @Param("wantedKeywords") List<String> wantedKeywords,
                                     @Param("unwantedKeywords") List<String> unwantedKeywords);
}
