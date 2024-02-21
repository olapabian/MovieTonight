package com.example.MovieTonight.algorithm;

import com.example.MovieTonight.model.database.GenresInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class FalseUser {

    private HashMap<GenresInfo,Long> pointsForGenre = new HashMap<>();

    public FalseUser(List<GenresInfo> allGenres) {
        for (GenresInfo genre : allGenres) {
            pointsForGenre.put(genre, 0L); //wszsytkie genre i startowe wartosci na 0;
        }
    }

    public void IncreasePoint(GenresInfo genre, Long amount){
        Long current = pointsForGenre.get(genre);
        Long newValue = current + amount;
        pointsForGenre.replace(genre,newValue);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Points for Genre:\n");
        for (Map.Entry<GenresInfo, Long> entry : pointsForGenre.entrySet()) {
            sb.append(entry.getKey().toString()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }
}
