package com.example.MovieTonight.dataFromApi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KeywordStatistic{
    private long genreId;
    private long occurrences;

    public void increaseOccurrences(){
        this.occurrences++;
    }

}