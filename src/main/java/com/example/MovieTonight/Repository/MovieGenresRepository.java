package com.example.MovieTonight.Repository;

import com.example.MovieTonight.Model.database.MovieGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieGenresRepository extends JpaRepository<MovieGenre,Long> {
}
