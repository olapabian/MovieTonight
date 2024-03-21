package com.example.MovieTonight.repository;

import com.example.MovieTonight.model.MovieReward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRewardsRepository extends JpaRepository<MovieReward,Long> {
}
