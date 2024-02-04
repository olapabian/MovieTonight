package com.example.MovieTonight.Repository;

import com.example.MovieTonight.Model.GenresInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenresInfoRepository extends JpaRepository<GenresInfo,Long> {
}
