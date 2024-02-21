package com.example.MovieTonight.dataFromApi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
public class MoviePointsStructure {
    private Long tmdbId;
    private Long genreId;
    private Long genreOccurences;

    public MoviePointsStructure(Long tmdbId, Long genreId, Long genreOccurences) {
        this.tmdbId = tmdbId;
        this.genreId = genreId;
        this.genreOccurences = genreOccurences;
    }
}
