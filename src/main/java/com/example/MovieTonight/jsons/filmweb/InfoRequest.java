package com.example.MovieTonight.jsons.filmweb;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InfoRequest {
    private boolean flag;
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

    public InfoRequest(boolean flag) {
        this.flag = flag;
    }

}
