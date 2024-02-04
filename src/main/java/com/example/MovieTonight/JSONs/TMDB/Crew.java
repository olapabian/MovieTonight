package com.example.MovieTonight.JSONs.TMDB;


import com.google.gson.annotations.SerializedName;

public class Crew {
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

    @SerializedName("credit_id")
    private String creditId;

    @SerializedName("department")
    private String department;

    @SerializedName("job")
    private String job;

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

    public int getId() {
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

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}

