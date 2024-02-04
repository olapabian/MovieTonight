package com.example.MovieTonight.Repository;

import com.example.MovieTonight.Model.RewardsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardsInfoRepository extends JpaRepository<RewardsInfo,Long> {
}
