package com.example.MovieTonight.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @Column(name = "question_id", nullable = false)
    private Long id;

    @Column(name = "content", length = Integer.MAX_VALUE)
    private String content;

    @Column(name = "type", length = Integer.MAX_VALUE)
    private String type;

}