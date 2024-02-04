package com.example.MovieTonight.Repository;

import com.example.MovieTonight.Model.ActorsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorsInfoRepository extends JpaRepository<ActorsInfo,Long> {
}
