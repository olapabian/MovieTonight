package com.example.MovieTonight.jsons.filmweb;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Type;
import java.util.List;

@Setter
@Getter
public class ProvidersRequest {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("path")
    private String path;

    @SerializedName("order")
    private int order;

    @SerializedName("promoted")
    private boolean promoted;

    @SerializedName("displayName")
    private String displayName;

    @SerializedName("link")
    private String link;

    @SerializedName("abonaments")
    private String abonaments;

    @SerializedName("hasFilms")
    private boolean hasFilms;

    @SerializedName("hasSerials")
    private boolean hasSerials;

    @SerializedName("hasTvshows")
    private boolean hasTvshows;

    @SerializedName("originalProducer")
    private boolean originalProducer;
    public static List<ProvidersRequest> fromJsonArray(String jsonArray) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<ProvidersRequest>>(){}.getType();
        return gson.fromJson(jsonArray, listType);
    }

}
