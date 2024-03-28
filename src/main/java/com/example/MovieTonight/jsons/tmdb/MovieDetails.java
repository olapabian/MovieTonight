package com.example.MovieTonight.jsons.tmdb;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class MovieDetails {
    @SerializedName("adult")
    private boolean adult;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("belongs_to_collection")
    private Object belongsToCollection;

    @SerializedName("budget")
    private int budget;

    @SerializedName("genres")
    private List<Genre> genres;

    @SerializedName("homepage")
    private String homepage;

    @SerializedName("id")
    private int id;

    @SerializedName("imdb_id")
    private String imdbId;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("overview")
    private String overview;

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("production_companies")
    private List<ProductionCompany> productionCompanies;

    @SerializedName("production_countries")
    private List<ProductionCountry> productionCountries;


    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("revenue")
    private String revenue;

    @SerializedName("runtime")
    private int runtime;

    @SerializedName("spoken_languages")
    private List<SpokenLanguage> spokenLanguages;

    @SerializedName("status")
    private String status;

    @SerializedName("tagline")
    private String tagline;

    @SerializedName("title")
    private String title;

    @SerializedName("video")
    private boolean video;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("vote_count")
    private int voteCount;

    @SerializedName("credits")
    private Credits credits;


    @Setter
    public static class Genre {
        @SerializedName("id")
        private int id;

        @Getter
        @SerializedName("name")
        private String name;

        public long getId() {
            return id;
        }


    }

    public static class ProductionCompany {
        @SerializedName("id")
        private int id;

        @SerializedName("logo_path")
        private String logoPath;

        @SerializedName("name")
        private String name;

        @SerializedName("origin_country")
        private String originCountry;

    }

    @Setter
    @Getter
    public static class ProductionCountry {
        @SerializedName("iso_3166_1")
        private String iso31661;

        @SerializedName("name")
        private String name;

    }

    public static class SpokenLanguage {
        @SerializedName("english_name")
        private String englishName;

        @SerializedName("iso_639_1")
        private String iso6391;

        @SerializedName("name")
        private String name;

    }

    @Setter
    @Getter
    public static class Credits {
        @SerializedName("cast")
        private List<Cast> cast;
        @SerializedName("crew")
        private List<Crew> crew;
    }

    @Setter
    public static class Cast {
        @Getter
        @SerializedName("adult")
        private boolean adult;

        @Getter
        @SerializedName("gender")
        private int gender;

        @SerializedName("id")
        private int id;

        @Getter
        @SerializedName("known_for_department")
        private String knownForDepartment;

        @Getter
        @SerializedName("name")
        private String name;

        @Getter
        @SerializedName("original_name")
        private String originalName;

        @Getter
        @SerializedName("popularity")
        private double popularity;

        @Getter
        @SerializedName("profile_path")
        private String profilePath;

        @Getter
        @SerializedName("cast_id")
        private int castId;

        @Getter
        @SerializedName("character")
        private String character;

        @Getter
        @SerializedName("credit_id")
        private String creditId;

        @Getter
        @SerializedName("order")
        private int order;

        public long getId() {
            return id;
        }

    }
}
