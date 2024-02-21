package com.example.MovieTonight.repository;

import com.example.MovieTonight.model.database.KeywordsInfo;
import com.example.MovieTonight.model.database.MovieGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KeywordsInfoRepository extends JpaRepository<KeywordsInfo, Long> {
}