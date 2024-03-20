package com.example.MovieTonight.repository;

import com.example.MovieTonight.model.QuizResults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizResultsRepository extends JpaRepository<QuizResults,Long> {
}
