package com.example.MovieTonight.jsons.tmdb;


import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
public class KeywordsRequest {
    @SerializedName("id")
    private int id;

    @Getter
    @SerializedName("keywords")
    private List<Keyword> keywords;

    public long getId() {
        return id;
    }

    @Setter
    public static class Keyword {
        @SerializedName("id")
        private int id;

        @Getter
        @SerializedName("name")
        private String name;

        public long getId() {
            return id;
        }

    }
}

