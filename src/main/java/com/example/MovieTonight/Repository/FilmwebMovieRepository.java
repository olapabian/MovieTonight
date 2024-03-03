package com.example.MovieTonight.Repository;

import com.example.MovieTonight.Model.database.FilmwebMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmwebMovieRepository extends JpaRepository<FilmwebMovie,Long> {

}
