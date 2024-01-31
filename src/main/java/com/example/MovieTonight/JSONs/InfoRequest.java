package com.example.MovieTonight.JSONs;

import com.google.gson.annotations.SerializedName;

public class InfoRequest {
    //klasa do serializacji JSONów
    @SerializedName("title")
    private String title;

    @SerializedName("originalTitle")
    private String originalTitle;

    @SerializedName("year")
    private int year;

    @SerializedName("type")
    private String type;

    @SerializedName("subType")
    private String subType;

    @SerializedName("posterPath")
    private String posterPath;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
