package com.example.MovieTonight.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "movie_images")
public class MovieImage {
    @Id
    @Column(name = "img_id", nullable = false)
//    private UUID id;
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Column(name = "type", length = Integer.MAX_VALUE)
    private String type;

    @Column(name = "img")
    private byte[] img;

}