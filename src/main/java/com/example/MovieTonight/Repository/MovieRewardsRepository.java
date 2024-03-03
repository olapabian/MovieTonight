package com.example.MovieTonight.Repository;

import com.example.MovieTonight.Model.database.MovieReward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRewardsRepository extends JpaRepository<MovieReward,Long> {
}
