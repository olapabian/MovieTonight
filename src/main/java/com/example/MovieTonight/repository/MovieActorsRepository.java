package com.example.MovieTonight.repository;

import com.example.MovieTonight.model.database.MovieActor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieActorsRepository extends JpaRepository<MovieActor,Long> {
}
