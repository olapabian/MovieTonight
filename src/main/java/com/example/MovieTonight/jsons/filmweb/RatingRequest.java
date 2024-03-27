package com.example.MovieTonight.jsons.filmweb;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RatingRequest {


    @SerializedName("count")
    private Long count;

    @SerializedName("rate")
    private String rate;

    @SerializedName("countWantToSee")
    private int countWantToSee;

    @SerializedName("countVote1")
    private int countVote1;

    @SerializedName("countVote2")
    private int countVote2;

    @SerializedName("countVote3")
    private int countVote3;

    @SerializedName("countVote4")
    private int countVote4;

    @SerializedName("countVote5")
    private int countVote5;

    @SerializedName("countVote6")
    private int countVote6;

    @SerializedName("countVote7")
    private int countVote7;

    @SerializedName("countVote8")
    private int countVote8;

    @SerializedName("countVote9")
    private int countVote9;

    @SerializedName("countVote10")
    private int countVote10;

}
