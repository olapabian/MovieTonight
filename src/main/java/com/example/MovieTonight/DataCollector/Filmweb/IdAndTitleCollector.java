package com.example.MovieTonight.DataCollector.Filmweb;


import com.example.MovieTonight.JSONs.InfoRequest;
import com.example.MovieTonight.JSONs.RatingRequest;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Service
@AllArgsConstructor
public class IdAndTitleCollector {
    public String Collect() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("filmwebIDs.txt"));
             BufferedWriter titleWriter = new BufferedWriter(new FileWriter("titles.txt"))) {

            int number = 0;

            for (int filmId = 1; number <= 100; filmId++) {
//                System.out.println(filmId);
                RatingRequest ratingRequest = new RatingRequest();
                String rating;
                double Rating;

                // -----------------Request rating (pobieram liczbe ocen i ocene)--------------------------------------------------
                URL url = new URL("https://www.filmweb.pl/api/v1/film/" + filmId + "/rating");
                HttpCollector ratingCollector = new HttpCollector(url);
                ratingCollector.Collect();

                if (!ratingCollector.getResponse().isEmpty()) {
                    String response = String.valueOf(ratingCollector.getResponse());
                    Gson gson = new Gson();
                    ratingRequest = gson.fromJson(response, RatingRequest.class);
                    rating = String.valueOf(ratingRequest.getRate());
                    Rating = Double.parseDouble(rating);
                } else {
                    continue;
                }

                if (Rating < 7.0) {
                    // jesli ocena jest mniejsza niz 8 idziemy dalej (nie bierzemy tego filmu)
                    continue;
                }

                if (ratingRequest.getCount() < 100000) {
                    // jesli liczba ocen jest mniejsza niz 200 000 idziemy dalej (nie bierzemy tego filmu)
                    continue;
                }

                //----------------Zapisuje ID filmwebowe------------------------------------
                writer.write(String.valueOf(filmId));
                writer.newLine();

                //----------------------------Request info (pobieram tylko tytuł)-------------------------------------
                URL url1 = new URL("https://www.filmweb.pl/api/v1/film/" + filmId + "/info");
                HttpCollector infoCollector = new HttpCollector(url1);
                infoCollector.Collect();

                if (!infoCollector.getResponse().isEmpty()) {
                    String response1 = String.valueOf(infoCollector.getResponse());
                    Gson gson1 = new Gson();
                    InfoRequest infoRequest = gson1.fromJson(response1, InfoRequest.class);

                    // Pobierz tytuł z obiektu InfoRequest
                    titleWriter.write(infoRequest.getTitle());
                    titleWriter.newLine();

//                    System.out.println();
                }

                number++;
            }
            return "pobrano dane";
        } catch (IOException e) {
            e.printStackTrace();
            return "błąd podczas zapisu danych";
        }
    }
}
