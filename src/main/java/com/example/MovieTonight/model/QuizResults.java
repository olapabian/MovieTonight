package com.example.MovieTonight.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "quiz_results")
public class QuizResults {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rowid", nullable = false)
    private Long id;

    @Column(name = "quiz_id")
    private Long quizId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "value_type")
    private String valueType;

    @Column(name = "value")
    private String value;

    @Column(name = "attitude")
    private String attitude;

    public QuizResults(Long quizId, Long userId, String valueType, String value, String attitude) {
        this.quizId = quizId;
        this.userId = userId;
        this.valueType = valueType;
        this.value = value;
        this.attitude = attitude;
    }

    public QuizResults(){

    }
}
