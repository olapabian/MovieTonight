package com.example.MovieTonight.repository;

import com.example.MovieTonight.model.database.MovieKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieKeywordRepository extends JpaRepository<MovieKeyword, Long> {
}