package com.example.MovieTonight.repository;

import com.example.MovieTonight.model.MovieProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieProvidersRepository extends JpaRepository<MovieProvider,Long> {
}
