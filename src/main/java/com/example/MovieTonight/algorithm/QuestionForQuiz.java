package com.example.MovieTonight.algorithm;

import com.example.MovieTonight.model.Question;

import java.util.List;

public class QuestionForQuiz {
    Question question;
    List<String> answers;

    public QuestionForQuiz(Question question, List<String> answers) {
        this.question = question;
        this.answers = answers;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }
}
