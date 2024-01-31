package com.example.MovieTonight.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "movie_providers")
public class MovieProvider {
    @Id
    @Column(name = "rowid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filmweb_id")
    private FilmwebMovie filmweb;

}