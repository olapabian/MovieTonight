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

}