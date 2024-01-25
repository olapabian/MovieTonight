package com.example.MovieTonight.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "filmweb_id")
    private Long filmwebId;

    @Column(name = "tmdb_id")
    private Long tmdbId;

    @Column(name = "title", length = Integer.MAX_VALUE)
    private String title;

    @Column(name = "original_title", length = Integer.MAX_VALUE)
    private String originalTitle;

    @OneToOne(mappedBy = "movie")
    private Actor actor;

    @OneToOne(mappedBy = "movie")
    private Director director;

    @OneToMany(mappedBy = "movie")
    private Set<MovieImage> movieImages = new LinkedHashSet<>();

}