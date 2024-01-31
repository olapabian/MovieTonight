package com.example.MovieTonight.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Column(name = "runtime")
    private Long runtime;

    @Column(name = "popularity")
    private Double popularity;

}