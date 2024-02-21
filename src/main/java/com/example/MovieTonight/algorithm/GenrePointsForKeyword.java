package com.example.MovieTonight.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SecondaryRow;

@AllArgsConstructor
@Getter
@Setter
public class GenrePointsForKeyword {
    Long genre_id;
    Long points;
}
