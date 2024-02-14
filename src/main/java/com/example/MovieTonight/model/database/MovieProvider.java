package com.example.MovieTonight.model.database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "movie_providers")
public class MovieProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rowid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filmweb_id")
    private FilmwebMovie filmweb;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id")
    private ProvidersInfo provider;

}