package com.example.MovieTonight.repository;

import com.example.MovieTonight.model.MovieDirector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieDirectorsRepository extends JpaRepository<MovieDirector,Long> {
}
