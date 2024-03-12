package com.example.MovieTonight.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class QuestionsResponse {
    private Long quizId;
    private List<QuestionForQuiz> questionForQuizList;
}
