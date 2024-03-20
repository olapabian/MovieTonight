package com.example.MovieTonight.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class GenreQuestionsResponse {
    private Long quizId;
    private List<GenreQuestion> questionForQuizList;
}
