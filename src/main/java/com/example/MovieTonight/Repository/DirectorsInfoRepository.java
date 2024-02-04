package com.example.MovieTonight.Repository;

import com.example.MovieTonight.Model.DirectorsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorsInfoRepository extends JpaRepository<DirectorsInfo,Long> {
}
