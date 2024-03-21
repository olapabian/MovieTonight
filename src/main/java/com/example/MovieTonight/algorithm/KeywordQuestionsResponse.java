package com.example.MovieTonight.algorithm;

import com.example.MovieTonight.model.KeywordsInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class KeywordQuestionsResponse {
    private Long quizId;
    private String question;
    private List<String> keywords;
}
