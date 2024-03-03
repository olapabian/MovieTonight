package com.example.MovieTonight.Repository;

import com.example.MovieTonight.Model.database.MovieProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieProvidersRepository extends JpaRepository<MovieProvider,Long> {
}
