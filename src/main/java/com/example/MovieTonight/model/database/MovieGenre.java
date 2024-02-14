package com.example.MovieTonight.model.database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "movie_genres")
public class MovieGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rowid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genre_id")
    private GenresInfo genre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tmdb_id")
    private TmdbMovie tmdbMovie;
}