package com.example.MovieTonight.Repository;

import com.example.MovieTonight.Model.MovieActor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieActorsRepository extends JpaRepository<MovieActor,Long> {
}
