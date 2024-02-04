package com.example.MovieTonight.JSONs.TMDB;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MovieSearchResponse {

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<GsonMovie> results;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("total_results")
    private int totalResults;

    public int getPage() {
        return page;
    }

    public List<GsonMovie> getResults() {
        return results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }
}

