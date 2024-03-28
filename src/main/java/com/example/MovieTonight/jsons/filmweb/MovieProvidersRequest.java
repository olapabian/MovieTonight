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
public class MovieProvidersRequest {
    @SerializedName("vodProvider")
    private int vodProvider;

    @SerializedName("id")
    private int id;

    @SerializedName("film")
    private int film;

    @SerializedName("start")
    private String start;

    @SerializedName("end")
    private String end;

    @SerializedName("link")
    private String link;

    @SerializedName("externalId")
    private String externalId;

    @SerializedName("status")
    private int status;

    @SerializedName("externalTitle")
    private String externalTitle;

    @SerializedName("payments")
    private List<Payment> payments;


    @Setter
    @Getter
    public static class Payment {
        @SerializedName("filmVod")
        private int filmVod;

        @SerializedName("accessType")
        private int accessType;

        @SerializedName("currency")
        private int currency;

        @SerializedName("price")
        private int price;

        @SerializedName("buy")
        private boolean buy;

        @SerializedName("rent")
        private boolean rent;

        @SerializedName("subscription")
        private boolean subscription;

        @SerializedName("free")
        private boolean free;

        @SerializedName("link")
        private String link;


    }
    public static List<MovieProvidersRequest> fromJsonArray(String jsonArray) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<MovieProvidersRequest>>(){}.getType();
        return gson.fromJson(jsonArray, listType);
    }
}
