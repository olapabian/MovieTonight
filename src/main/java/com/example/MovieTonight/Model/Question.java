package com.example.MovieTonight.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rowid", nullable = false)
    private Long id;

    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "content", length = Integer.MAX_VALUE)
    private String content;

    @Column(name = "type", length = Integer.MAX_VALUE)
    private String type;

}