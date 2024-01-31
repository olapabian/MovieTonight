package com.example.MovieTonight.Repository;

import com.example.MovieTonight.Model.ProvidersInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvidersInfoRepository extends JpaRepository<ProvidersInfo,Long> {
}
