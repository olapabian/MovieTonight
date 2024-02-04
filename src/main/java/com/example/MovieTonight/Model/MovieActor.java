package com.example.MovieTonight.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "movie_actors")
public class MovieActor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rowid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id")
    private ActorsInfo actor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tmdb_id")
    private TmdbMovie tmdbMovie;

}