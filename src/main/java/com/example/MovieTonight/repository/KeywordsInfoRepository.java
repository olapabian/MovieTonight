package com.example.MovieTonight.repository;

import com.example.MovieTonight.model.database.KeywordsInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordsInfoRepository extends JpaRepository<KeywordsInfo, Long> {
}