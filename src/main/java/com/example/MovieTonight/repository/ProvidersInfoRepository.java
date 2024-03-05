package com.example.MovieTonight.repository;

import com.example.MovieTonight.model.ProvidersInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvidersInfoRepository extends JpaRepository<ProvidersInfo,Long> {
}
