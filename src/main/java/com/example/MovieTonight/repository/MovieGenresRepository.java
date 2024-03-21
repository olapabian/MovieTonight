package com.example.MovieTonight.repository;

import com.example.MovieTonight.model.MovieGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieGenresRepository extends JpaRepository<MovieGenre,Long> {
}