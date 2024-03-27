package com.example.MovieTonight.DataCollector.Filmweb;

import com.example.MovieTonight.Model.database.GenreImages;
import com.example.MovieTonight.Model.database.GenresInfo;
import com.example.MovieTonight.Repository.GenreImagesRepository;
import com.example.MovieTonight.Repository.GenresInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GenresPhotosCollector {

    private final GenreImagesRepository genreImagesRepository;
    private final GenresInfoRepository genresInfoRepository;

    //    @PostConstruct
    public void insertPhotoGenre() throws Exception {
        GenreImages genreImages = new GenreImages();
        // Dla gatunku animacja (16)
        // Optional<GenresInfo> genresInfoOptional = genresInfoRepository.findById(16L);
        Optional<GenresInfo> genresInfoOptional = genresInfoRepository.findById(10749L);
        if (genresInfoOptional.isPresent()) {
            GenresInfo genresInfo = genresInfoOptional.get();
            URL postreImgUrl = new URL("https://s3.viva.pl/newsy/kadr-z-filmu-tulipanowa-goraczka-1-681526-GALLERY_BIG.jpg");
            HttpCollector posterCollector = new HttpCollector(postreImgUrl);
            posterCollector.Collect();

            byte[] photoImgBytes = posterCollector.getResponseBytes();

            if (photoImgBytes.length > 0) {
                genreImages.setGenresInfo(genresInfo);
                genreImages.setImg(photoImgBytes);
                genreImagesRepository.save(genreImages);
            }
        }
    }
}



