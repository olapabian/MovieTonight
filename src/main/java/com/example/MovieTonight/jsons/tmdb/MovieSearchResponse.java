package com.example.MovieTonight.jsons.tmdb;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.List;

@Getter
public class MovieSearchResponse {

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<GsonMovie> results;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("total_results")
    private int totalResults;

}

