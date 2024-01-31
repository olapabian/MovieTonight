package com.example.MovieTonight.JSONs;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

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
    public int getId() {
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isPromoted() {
        return promoted;
    }

    public void setPromoted(boolean promoted) {
        this.promoted = promoted;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAbonaments() {
        return abonaments;
    }

    public void setAbonaments(String abonaments) {
        this.abonaments = abonaments;
    }

    public boolean isHasFilms() {
        return hasFilms;
    }

    public void setHasFilms(boolean hasFilms) {
        this.hasFilms = hasFilms;
    }

    public boolean isHasSerials() {
        return hasSerials;
    }

    public void setHasSerials(boolean hasSerials) {
        this.hasSerials = hasSerials;
    }

    public boolean isHasTvshows() {
        return hasTvshows;
    }

    public void setHasTvshows(boolean hasTvshows) {
        this.hasTvshows = hasTvshows;
    }

    public boolean isOriginalProducer() {
        return originalProducer;
    }

    public void setOriginalProducer(boolean originalProducer) {
        this.originalProducer = originalProducer;
    }
}
