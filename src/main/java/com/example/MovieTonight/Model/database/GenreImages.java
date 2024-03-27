package com.example.MovieTonight.Model.database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "genre_images")
@Getter
@Setter
@NoArgsConstructor
public class GenreImages {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private GenresInfo genresInfo;

    @Column(name = "img")
    private byte[] img;

}
