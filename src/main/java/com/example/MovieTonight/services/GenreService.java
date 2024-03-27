package com.example.MovieTonight.services;

import com.example.MovieTonight.Model.database.GenreImages;
import com.example.MovieTonight.Repository.GenreImagesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GenreService {
    private final GenreImagesRepository genreImagesRepository;

    public byte[] getGenreImg(Long id) {
        GenreImages genreImages = new GenreImages();
        Optional<GenreImages> genreImagesOptional = genreImagesRepository.findByGenresInfoId(id);
        if (genreImagesOptional.isPresent()) {
            genreImages = genreImagesOptional.get();
        }
        return genreImages.getImg();
    }
}
