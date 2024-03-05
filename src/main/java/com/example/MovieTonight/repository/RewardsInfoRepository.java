package com.example.MovieTonight.repository;

import com.example.MovieTonight.model.RewardsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardsInfoRepository extends JpaRepository<RewardsInfo,Long> {
}
