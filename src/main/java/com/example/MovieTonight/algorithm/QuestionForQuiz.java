package com.example.MovieTonight.algorithm;

import com.example.MovieTonight.model.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class QuestionForQuiz {
    private Question question;
    private List<String> answers;

}
