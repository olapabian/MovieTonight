package com.example.MovieTonight.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class MovieRecommendationRequest {
    private Long quizId;
    private Long userId;
    private List<String> keywords;
}
