package com.example.MovieTonight.Repository;

import com.example.MovieTonight.Model.MovieDirector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieDirectorsRepository extends JpaRepository<MovieDirector,Long> {
}
