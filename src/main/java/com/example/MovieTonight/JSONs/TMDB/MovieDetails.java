package com.example.MovieTonight.JSONs.TMDB;

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

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Object getBelongsToCollection() {
        return belongsToCollection;
    }

    public void setBelongsToCollection(Object belongsToCollection) {
        this.belongsToCollection = belongsToCollection;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public List<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(List<ProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public List<ProductionCountry> getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(List<ProductionCountry> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public List<SpokenLanguage> getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(List<SpokenLanguage> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public Credits getCredits() {
        return credits;
    }

    public void setCredits(Credits credits) {
        this.credits = credits;
    }
    // Gettery i settery

    public static class Genre {
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
// Gettery i settery
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

        // Gettery i settery
    }

    public static class ProductionCountry {
        @SerializedName("iso_3166_1")
        private String iso31661;

        @SerializedName("name")
        private String name;

        // Gettery i settery

        public String getIso31661() {
            return iso31661;
        }

        public void setIso31661(String iso31661) {
            this.iso31661 = iso31661;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class SpokenLanguage {
        @SerializedName("english_name")
        private String englishName;

        @SerializedName("iso_639_1")
        private String iso6391;

        @SerializedName("name")
        private String name;

        // Gettery i settery
    }

    public static class Credits {
        @SerializedName("cast")
        private List<Cast> cast;
        @SerializedName("crew")
        private List<Crew> crew;

        public List<Crew> getCrew() {
            return crew;
        }

        public void setCrew(List<Crew> crew) {
            this.crew = crew;
        }

        public List<Cast> getCast() {
            return cast;
        }

        public void setCast(List<Cast> cast) {
            this.cast = cast;
        }
// Gettery i settery
    }

    public static class Cast {
        @SerializedName("adult")
        private boolean adult;

        @SerializedName("gender")
        private int gender;

        @SerializedName("id")
        private int id;

        @SerializedName("known_for_department")
        private String knownForDepartment;

        @SerializedName("name")
        private String name;

        @SerializedName("original_name")
        private String originalName;

        @SerializedName("popularity")
        private double popularity;

        @SerializedName("profile_path")
        private String profilePath;

        @SerializedName("cast_id")
        private int castId;

        @SerializedName("character")
        private String character;

        @SerializedName("credit_id")
        private String creditId;

        @SerializedName("order")
        private int order;

        // Gettery i settery

        public boolean isAdult() {
            return adult;
        }

        public void setAdult(boolean adult) {
            this.adult = adult;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public long getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getKnownForDepartment() {
            return knownForDepartment;
        }

        public void setKnownForDepartment(String knownForDepartment) {
            this.knownForDepartment = knownForDepartment;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOriginalName() {
            return originalName;
        }

        public void setOriginalName(String originalName) {
            this.originalName = originalName;
        }

        public double getPopularity() {
            return popularity;
        }

        public void setPopularity(double popularity) {
            this.popularity = popularity;
        }

        public String getProfilePath() {
            return profilePath;
        }

        public void setProfilePath(String profilePath) {
            this.profilePath = profilePath;
        }

        public int getCastId() {
            return castId;
        }

        public void setCastId(int castId) {
            this.castId = castId;
        }

        public String getCharacter() {
            return character;
        }

        public void setCharacter(String character) {
            this.character = character;
        }

        public String getCreditId() {
            return creditId;
        }

        public void setCreditId(String creditId) {
            this.creditId = creditId;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }
    }
}
