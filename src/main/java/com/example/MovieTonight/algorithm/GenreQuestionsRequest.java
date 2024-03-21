package com.example.MovieTonight.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class GenreQuestionsRequest {
    private Long quizId;
    private Long userId;
    private String side;
    private String time;
    private String releaseDate;
    private String adult;
}
