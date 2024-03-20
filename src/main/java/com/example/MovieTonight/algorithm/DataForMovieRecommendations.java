package com.example.MovieTonight.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class DataForMovieRecommendations {
    private List<Long> wantedGenres;
    private List<Long> unwantedGenres;
    private List<Long> wantedKeywords;
    private Long minTime;
    private Long maxTime;
    private Long minReleaseDate;
    private Long maxReleaseDate;
    private String adult;
}
