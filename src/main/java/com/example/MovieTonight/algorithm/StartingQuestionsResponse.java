package com.example.MovieTonight.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class StartingQuestionsResponse {
    private String sideQuestion;
    private List<String> sideAnswer;
    private String timeQuestion;
    private List<String> timeAnswer;
    private String releaseDateQuestion;
    private String adultQuestion;
    private List<String> adultAnswer;
}
