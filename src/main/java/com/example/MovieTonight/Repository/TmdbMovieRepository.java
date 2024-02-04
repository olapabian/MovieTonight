package com.example.MovieTonight.Repository;

import com.example.MovieTonight.Model.TmdbMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TmdbMovieRepository extends JpaRepository<TmdbMovie,Long> {
}
