package com.example.MovieTonight.dataCollector.filmweb;


import com.example.MovieTonight.dataCollector.tmdb.ApiCollector;
import com.example.MovieTonight.jsons.filmweb.InfoRequest;
import com.example.MovieTonight.jsons.filmweb.RatingRequest;
import com.example.MovieTonight.jsons.tmdb.GsonMovie;
import com.example.MovieTonight.jsons.tmdb.MovieSearchResponse;
import com.google.gson.Gson;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class IdAndTitleCollector {
    private static final Logger logger = LoggerFactory.getLogger(FilmwebCollector.class);


    public void collect(boolean is_test) {
        if (!is_test) {
            List<Thread> threads = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                int from = i * 100000;
                int to = (i + 1) * 100000;
                int finalI = i;
                Thread thread = new Thread(() -> collectPart(from, to, String.valueOf(finalI)));
                thread.start();
                threads.add(thread);
            }
            waitToThreadsEnd(threads);
        } else {
            System.out.println("id and title collector włącza sie");
        }
    }

    public void waitToThreadsEnd(List<Thread> threads) {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                logger.info("waitToThreadsEnd error");
            }
        }
        System.out.println("Wszystkie wątki zakończone.");
    }


    public void collectPart(int from, int to, String number) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("filmwebIDs" + number + ".txt"));
             BufferedWriter titleWriter = new BufferedWriter(new FileWriter("titles" + number + ".txt"));
             BufferedWriter yearWriter = new BufferedWriter(new FileWriter("years" + number + ".txt"))) {
            IntStream.range(from, to).forEach(filmId -> {
                try {
                    InfoRequest infoRequest = isMovie(String.valueOf(filmId));
                    if (infoRequest.isFlag()) {
                        if (isPopular(String.valueOf(filmId)) && hasProviders(String.valueOf(filmId)) && isFindedInTmdb(infoRequest)) {
                            saveIdTitleAndYearToFile(writer, titleWriter, yearWriter, filmId, infoRequest);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });


        } catch (IOException e) {
            System.out.println("błąd podczas zapisu danych");
        }
    }

    private void saveIdTitleAndYearToFile(BufferedWriter writer, BufferedWriter titleWriter, BufferedWriter yearWriter, int filmId, InfoRequest infoRequest) throws IOException {
        writer.write(String.valueOf(filmId));
        writer.newLine();
        if (infoRequest.getOriginalTitle() != null && !infoRequest.getOriginalTitle().isEmpty()) {
            titleWriter.write(infoRequest.getOriginalTitle());
            titleWriter.newLine();
        } else {
            titleWriter.write(infoRequest.getTitle());
            titleWriter.newLine();
        }
        yearWriter.write(String.valueOf(infoRequest.getYear()));
        yearWriter.newLine();
    }

    public boolean isFindedInTmdb(InfoRequest infoRequest) throws IOException {
        boolean isFinded = false;
        String title;
        if (infoRequest.getOriginalTitle() != null && !infoRequest.getOriginalTitle().isEmpty()) {
            title = infoRequest.getOriginalTitle();

        } else {
            title = infoRequest.getTitle();
        }
        System.out.println(title);
        String url = "https://api.themoviedb.org/3/search/movie?api_key=&query=" + title;
        ApiCollector apiCollector = new ApiCollector();
        Response response = apiCollector.collect(url);
        if (response.isSuccessful()) {
            assert response.body() != null;
            String jsonResponse = response.body().string();

            if (!jsonResponse.isEmpty()) {
                Gson gson = new Gson();
                MovieSearchResponse movieSearchResponse = gson.fromJson(jsonResponse, MovieSearchResponse.class);
                for (int i = 0; i < movieSearchResponse.getResults().size(); ++i) {
                    GsonMovie gsonMovie = movieSearchResponse.getResults().get(i);
                    if (gsonMovie.getReleaseDate() != null && gsonMovie.getReleaseDate().length() >= 4) {
                        if (gsonMovie.getOriginalTitle().equals(title) && gsonMovie.getReleaseDate().substring(0, 4).equals(String.valueOf(infoRequest.getYear()))) {
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
        return isFinded;
    }


    private InfoRequest isMovie(String filmId) throws IOException {
        URL url1 = new URL("https://www.filmweb.pl/api/v1/film/" + filmId + "/info");
        HttpCollector infoCollector = new HttpCollector(url1);
        infoCollector.collect();
        InfoRequest infoRequest = new InfoRequest(false);
        if (!infoCollector.getResponse().isEmpty()) {
            String response1 = String.valueOf(infoCollector.getResponse());
            Gson gson1;
            gson1 = new Gson();
            infoRequest = gson1.fromJson(response1, InfoRequest.class);
            infoRequest.setFlag(infoRequest.getType().equals("film"));
        } else {
            infoRequest.setFlag(false);
        }
        return infoRequest;
    }

    private boolean isPopular(String filmId) throws IOException {
        RatingRequest ratingRequest;

        URL url = new URL("https://www.filmweb.pl/api/v1/film/" + filmId + "/rating");
        HttpCollector ratingCollector = new HttpCollector(url);
        ratingCollector.collect();

        if (!ratingCollector.getResponse().isEmpty()) {
            String response = String.valueOf(ratingCollector.getResponse());
            Gson gson = new Gson();
            ratingRequest = gson.fromJson(response, RatingRequest.class);
        } else {
            System.out.println("pod tym id nie ma filmu ani nic");
            return false;
        }

        if (ratingRequest.getCount() == null || ratingRequest.getCount() < 200) {
            System.out.println("oceny wykluczyły film");
            return false;
        }
        System.out.println("sprawdzono oceny");
        return true;
    }

    private boolean hasProviders(String filmId) {
        try {
            URL url2 = new URL("https://www.filmweb.pl/api/v1/vod/film/" + filmId + "/providers/list");
            HttpCollector httpCollector = new HttpCollector(url2);
            httpCollector.collect();
            if (httpCollector.getResponse().isEmpty() || httpCollector.getResponse() == null) {
                System.out.println("nie ma providerow");
                return false;
            }
        } catch (FileNotFoundException e) {
            System.out.println("nie ma providerow");
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("sprawdzono providers");
        return true;
    }


}


