package com.example.MovieTonight.Repository;

import com.example.MovieTonight.Model.database.GenreImages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface GenreImagesRepository extends JpaRepository<GenreImages, Long> {
    Optional<GenreImages> findByGenresInfoId(Long id);
}