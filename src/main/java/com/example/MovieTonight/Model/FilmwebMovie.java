package com.example.MovieTonight.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "filmweb_movies")
public class FilmwebMovie {
    @Id
    @Column(name = "filmweb_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "providers")
    private Provider providers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rewards")
    private Reward rewards;

    @Column(name = "release_date")
    private Long releaseDate;

    @Column(name = "rating", length = Integer.MAX_VALUE)
    private String rating;

    @Column(name = "rating_count", length = Integer.MAX_VALUE)
    private String ratingCount;

}