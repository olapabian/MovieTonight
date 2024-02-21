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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "keyword_id")
    private KeywordsInfo keywordId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genre_id")
    private GenresInfo genreId;

    @Column(name = "points", nullable = false)
    private Long points;

}
