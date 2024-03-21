package com.example.MovieTonight.model;

import com.example.MovieTonight.enums.ValueType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "answers")
public class Answers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rowid", nullable = false)
    private Long id;

    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "content")
    private String content;

    @Column(name = "value")
    private String value;

    @Column(name = "value_type")
    @Enumerated(EnumType.STRING)
    private ValueType valueType;
}
