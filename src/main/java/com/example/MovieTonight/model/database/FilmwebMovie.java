package com.example.MovieTonight.model.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "filmweb_movies")
public class FilmwebMovie {
    @Id
    @Column(name = "filmweb_id", nullable = false)
    private Long id;

    @Column(name = "release_date")
    private Long releaseDate;

    @Column(name = "rating", length = Integer.MAX_VALUE)
    private String rating;

    @Column(name = "rating_count", length = Integer.MAX_VALUE)
    private String ratingCount;

    public FilmwebMovie(Long releaseDate) {
        this.releaseDate = releaseDate;
    }
}