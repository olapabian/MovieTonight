package com.example.MovieTonight.JSONs.TMDB;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KeywordsRequest {
    @SerializedName("id")
    private int id;

    @SerializedName("keywords")
    private List<Keyword> keywords;

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }

    public static class Keyword {
        @SerializedName("id")
        private int id;

        @SerializedName("name")
        private String name;

        public long getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

