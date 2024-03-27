package com.example.MovieTonight.model.database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filmweb_id")
    private FilmwebMovie filmweb;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tmdb_id")
    private TmdbMovie tmdb;

    @Column(name = "title", length = Integer.MAX_VALUE)
    private String title;

    @Column(name = "original_title", length = Integer.MAX_VALUE)
    private String originalTitle;

    public Movie(String title, String originalTitle, FilmwebMovie filmwebMovie) {
        this.title = title;
        this.originalTitle = originalTitle;
        this.filmweb = filmwebMovie;
    }

}