package com.example.MovieTonight.Repository;

import com.example.MovieTonight.Model.MovieKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieKeywordRepository extends JpaRepository<MovieKeyword, Long> {
}