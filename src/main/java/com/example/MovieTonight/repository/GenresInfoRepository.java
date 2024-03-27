package com.example.MovieTonight.repository;

import com.example.MovieTonight.model.database.GenresInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenresInfoRepository extends JpaRepository<GenresInfo,Long> {
}
