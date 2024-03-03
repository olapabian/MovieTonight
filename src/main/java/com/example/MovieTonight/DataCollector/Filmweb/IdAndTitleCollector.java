package com.example.MovieTonight.DataCollector.Filmweb;


import com.example.MovieTonight.DataCollector.TMDB.ApiCollector;
import com.example.MovieTonight.JSONs.Filmweb.InfoRequest;
import com.example.MovieTonight.JSONs.Filmweb.RatingRequest;
import com.example.MovieTonight.JSONs.TMDB.GsonMovie;
import com.example.MovieTonight.JSONs.TMDB.MovieSearchResponse;
import com.example.MovieTonight.Model.others.InfoAndBool;
import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

@Service
@AllArgsConstructor
public class
IdAndTitleCollector {
    @PostConstruct
    public void collect() {
        int from = 0;
        int to = 10;

        for (int number = 0; number < 10; ++number) {
            collectPart(from, to, String.valueOf(number));
//            from += 100000;
//            to += 100000;
            from += 10;
            to += 10;
        }

    }

    public void collectPart(int from, int to, String number) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("filmwebIDs" + number + ".txt"));
             BufferedWriter titleWriter = new BufferedWriter(new FileWriter("titles" + number + ".txt"));
             BufferedWriter yearWriter = new BufferedWriter(new FileWriter("years" + number + ".txt"))) {
            for (int filmId = from; filmId < to; filmId++)
//            for (int filmId = 1; filmId <= 1; filmId++)
            {
                System.out.println(filmId);
                InfoAndBool infoAndBool = isCorrectMovie(String.valueOf(filmId));

                if (infoAndBool.getFlag()) {
                    System.out.println(infoAndBool.getInfoRequest().getTitle());


                    //Request search sprawdzam czy znajde go w tmdb
                    boolean isFinded = false;
                    String title = new String();
                    if (infoAndBool.getInfoRequest().getOriginalTitle() != null && !infoAndBool.getInfoRequest().getOriginalTitle().isEmpty()) {
                        title = infoAndBool.getInfoRequest().getOriginalTitle();
                    } else {
                        infoAndBool.getInfoRequest().getTitle();
                    }
                    String url = "https://api.themoviedb.org/3/search/movie?api_key=&query=" + title;
                    ApiCollector apiCollector = new ApiCollector();
                    Response response = apiCollector.collect(url);
                    if (response.isSuccessful()) {
                        String jsonResponse = response.body().string();

                        if (jsonResponse != null && !jsonResponse.isEmpty()) {
                            Gson gson = new Gson();
                            MovieSearchResponse movieSearchResponse = gson.fromJson(jsonResponse, MovieSearchResponse.class);

                            //petla po liscie wynikow wyszukiwania filmu
                            for (int i = 0; i < movieSearchResponse.getResults().size(); ++i) {
                                GsonMovie gsonMovie = movieSearchResponse.getResults().get(i);
                                if (gsonMovie.getReleaseDate() != null && gsonMovie.getReleaseDate().length() >= 4) {
                                    //mudsze year przekazac z funkcji colelct
                                    if (gsonMovie.getOriginalTitle().equals(title) && gsonMovie.getReleaseDate().substring(0, 4).equals(String.valueOf(infoAndBool.getInfoRequest().getYear()))) {
                                        //TMDB Id
                                        System.out.println(gsonMovie.getTitle());
                                        isFinded = true;
                                        break;
                                    }
                                }
                            }
                        } else {
                            System.out.println("Empty or null response body");
                        }
                    } else {
                        System.out.println("Błąd: " + response.code() + " - " + response.message());
                    }

                    if (isFinded) {
                        response.body().close();
                        writer.write(String.valueOf(filmId));
                        writer.newLine();
                        // Pobierz tytuł z obiektu InfoRequest
                        if (infoAndBool.getInfoRequest().getOriginalTitle() != null && !infoAndBool.getInfoRequest().getOriginalTitle().isEmpty()) {
                            titleWriter.write(infoAndBool.getInfoRequest().getOriginalTitle());
                            titleWriter.newLine();
                        } else {
                            titleWriter.write(infoAndBool.getInfoRequest().getTitle());
                            titleWriter.newLine();
                        }
                        yearWriter.write(String.valueOf(infoAndBool.getInfoRequest().getYear()));
                        yearWriter.newLine();
                    } else {
                        System.out.println("nie znaleziono tego filmu w tmdb ");
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("błąd podczas zapisu danych");
        }
    }

    //Checking is that movie and how popular is
    public InfoAndBool isCorrectMovie(String filmId) throws IOException {
        InfoAndBool infoAndBool = null;
        try {
            infoAndBool = new InfoAndBool();
            RatingRequest ratingRequest = new RatingRequest();
            String rating;
            double Rating = 0;

            // -----------------Request rating rating and rating count needed to check popularity--------------------------------------------------
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
                System.out.println("pod tym id nie ma filmu ani nic");
                infoAndBool.setFlag(false);
                return infoAndBool;
            }

            if (Rating < 5.0) {
                System.out.println("oceny wykluczyły film");
                infoAndBool.setFlag(false);
                return infoAndBool;
            }

            if (ratingRequest.getCount() == null || ratingRequest.getCount().longValue() < 2000) {
                System.out.println("oceny wykluczyły film");
                infoAndBool.setFlag(false);
                return infoAndBool;
            }
            System.out.println("sprawdzono oceny");


            //----------------------------Request providers check are some providers-------------------------------------
            try {
                URL url2 = new URL("https://www.filmweb.pl/api/v1/vod/film/" + filmId + "/providers/list");
                HttpCollector httpCollector = new HttpCollector(url2);
                httpCollector.Collect();
                if (httpCollector.getResponse().isEmpty() || httpCollector.getResponse() == null) {
                    infoAndBool.setFlag(false);
                    System.out.println("nie ma providerowwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
                    return infoAndBool;
                }
            } catch (FileNotFoundException e) {
                infoAndBool.setFlag(false);
                System.out.println("nie ma providerowwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
                return infoAndBool;
            }
            System.out.println("sprawdzono providers");
            //----------------------------Request info to check is that film-------------------------------------
            URL url1 = new URL("https://www.filmweb.pl/api/v1/film/" + filmId + "/info");
            HttpCollector infoCollector = new HttpCollector(url1);
            infoCollector.Collect();

            if (!infoCollector.getResponse().isEmpty()) {
                String response1 = String.valueOf(infoCollector.getResponse());
                Gson gson1;
                gson1 = new Gson();
                InfoRequest infoRequest = gson1.fromJson(response1, InfoRequest.class);
                infoAndBool.setFlag(infoRequest.getType().equals("film"));
                if (infoRequest.getType().equals("film")) {
                    System.out.println(" to film");
                } else {
                    System.out.println("to nie film");
                }
                infoAndBool.setInfoRequest(infoRequest);
                return infoAndBool;
            }
            boolean isFinded = false;

        } catch (Exception e) {
            System.out.println(e);
            isCorrectMovie(filmId);
        }
        return infoAndBool;
    }

}


