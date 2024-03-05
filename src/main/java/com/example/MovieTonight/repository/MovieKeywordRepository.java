package com.example.MovieTonight.repository;

import com.example.MovieTonight.model.GenresInfo;
import com.example.MovieTonight.model.MovieKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieKeywordRepository extends JpaRepository<MovieKeyword, Long> {

}
