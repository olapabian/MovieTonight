package com.example.MovieTonight.repository;

import com.example.MovieTonight.model.DirectorsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorsInfoRepository extends JpaRepository<DirectorsInfo,Long> {
}
