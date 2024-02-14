package com.example.MovieTonight.model.database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "keyword_points")
public class KeywordPoints {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rowid", nullable = false)
    private Long id;

    @Column(name = "keyword_id", nullable = false)
    private Long keywordId;

    @Column(name = "genre_id", nullable = false)
    private Long genreId;

    @Column(name = "genreOccurrences", nullable = false)
    private Long genreOccurrences;

}
