package com.example.MovieTonight.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tmdb_movies")
public class TmdbMovie {
    @Id
    @Column(name = "tmdb_id", nullable = false)
    private Long id;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "production_countries", length = Integer.MAX_VALUE)
    private String productionCountries;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genres")
    private Genre genres;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keywords")
    private Keyword keywords;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actors")
    private Actor actors;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "director")
    private Director director;

    @Column(name = "runtime")
    private Long runtime;

}