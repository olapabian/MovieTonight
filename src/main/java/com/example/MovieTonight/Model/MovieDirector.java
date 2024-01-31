package com.example.MovieTonight.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "movie_directors")
public class MovieDirector {
    @Id
    @Column(name = "rowid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "director_id")
    private DirectorsInfo director;

    @Column(name = "tmdb_id")
    private Long tmdbId;

}