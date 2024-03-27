package com.example.MovieTonight.dataCollector.filmweb;

import com.example.MovieTonight.model.database.GenreImages;
import com.example.MovieTonight.model.database.GenresInfo;
import com.example.MovieTonight.repository.GenreImagesRepository;
import com.example.MovieTonight.repository.GenresInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GenresPhotosCollector {

    private final GenreImagesRepository genreImagesRepository;
    private final GenresInfoRepository genresInfoRepository;

    public void insertPhotoGenre() throws Exception {
        GenreImages genreImages = new GenreImages();

        Optional<GenresInfo> genresInfoOptional = genresInfoRepository.findById(10749L);
        if (genresInfoOptional.isPresent()) {
            GenresInfo genresInfo = genresInfoOptional.get();
            URL postreImgUrl = new URL("https://s3.viva.pl/newsy/kadr-z-filmu-tulipanowa-goraczka-1-681526-GALLERY_BIG.jpg");
            HttpCollector posterCollector = new HttpCollector(postreImgUrl);
            posterCollector.collect();

            byte[] photoImgBytes = posterCollector.getResponseBytes();

            if (photoImgBytes.length > 0) {
                genreImages.setGenresInfo(genresInfo);
                genreImages.setImg(photoImgBytes);
                genreImagesRepository.save(genreImages);
            }
        }
    }
}



