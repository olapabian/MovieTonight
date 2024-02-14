package com.example.MovieTonight.repository;

import com.example.MovieTonight.model.database.KeywordPoints;
import com.example.MovieTonight.model.database.MovieDirector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordPointsRepository extends JpaRepository<KeywordPoints,Long> {
}

