package com.example.MovieTonight.dataCollector.filmweb;


import com.example.MovieTonight.jsons.filmweb.InfoRequest;
import com.example.MovieTonight.jsons.filmweb.MovieProvidersRequest;
import com.example.MovieTonight.jsons.filmweb.ProvidersRequest;
import com.example.MovieTonight.jsons.filmweb.RatingRequest;
import com.example.MovieTonight.model.database.FilmwebMovie;
import com.example.MovieTonight.model.database.Movie;
import com.example.MovieTonight.model.database.MovieProvider;
import com.example.MovieTonight.model.database.ProvidersInfo;
import com.example.MovieTonight.repository.FilmwebMovieRepository;
import com.example.MovieTonight.repository.MovieProvidersRepository;
import com.example.MovieTonight.repository.MovieRepository;
import com.example.MovieTonight.repository.ProvidersInfoRepository;
import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class FilmwebCollector {
    private static final Logger logger = LoggerFactory.getLogger(FilmwebCollector.class);

    private final MovieRepository movieRepository;
    private final FilmwebMovieRepository filmwebMovieRepository;
    private final ProvidersInfoRepository providersInfoRepository;
    private final MovieProvidersRepository movieProvidersRepository;

    public void filmwebCollect(String filePath, boolean is_test) {
        if (!is_test) {
            processMovies(filePath);
            processProviders(filePath);
            System.out.println("pobrano dane " + filePath);
        } else {
            System.out.println("filmweb collector działa");
        }
    }

    private void processMovies(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            List<Movie> movieList = new ArrayList<>();
            List<FilmwebMovie> filmwebMovieList = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                Movie movie = collectMovie(line);
                movieList.add(movie);
                filmwebMovieList.add(movie.getFilmweb());
            }
            filmwebMovieRepository.saveAll(filmwebMovieList);
            System.out.println("pobrano do filmweb_movie");
            movieRepository.saveAll(movieList);
            System.out.println("pobrano do movie");
        } catch (IOException e) {
            logger.info("processMovies");
        }
    }

    public void processProviders(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                collectMovieProviders(line);
            }
        } catch (IOException e) {
            logger.info("processProviders");
        }
    }

    public Movie collectMovie(String filmwebId) throws IOException {
        Movie movie = null;
        try {
            movie = fetchMovieInfo(filmwebId);
            FilmwebMovie filmwebMovie = fetchMovieRating(filmwebId, movie.getFilmweb());
            movie.setFilmweb(filmwebMovie);
        } catch (Exception e) {
            logger.info("collectMovie");
            collectMovie(filmwebId);
        }
        System.out.println("pobrano rating");
        return movie;
    }

    private Movie fetchMovieInfo(String filmwebId) throws IOException {
        Long releaseDateAsLong = null;
        String originalTitleAsString = null;
        String titleAsString = null;
        URL url = new URL("https://www.filmweb.pl/api/v1/film/" + filmwebId + "/info");
        HttpCollector httpCollector = new HttpCollector(url);
        httpCollector.collect();
        if (!httpCollector.getResponse().isEmpty()) {
            String response = String.valueOf(httpCollector.getResponse());
            Gson gson = new Gson();
            InfoRequest infoRequest = gson.fromJson(response, InfoRequest.class);
            System.out.println("filmwebID" + filmwebId);
            titleAsString = String.valueOf(infoRequest.getTitle());

            if (infoRequest.getOriginalTitle() != null && !infoRequest.getOriginalTitle().isEmpty()) {
                originalTitleAsString = infoRequest.getOriginalTitle();
            } else originalTitleAsString = String.valueOf(infoRequest.getTitle());

            releaseDateAsLong = (long) infoRequest.getYear();
            System.out.println("pobrano info" + infoRequest.getTitle());
        }

        FilmwebMovie filmwebMovie = new FilmwebMovie(releaseDateAsLong);
        return new Movie(titleAsString, originalTitleAsString, filmwebMovie);
    }

    private FilmwebMovie fetchMovieRating(String filmwebId, FilmwebMovie filmwebMovie) throws IOException {
        URL url1 = new URL("https://www.filmweb.pl/api/v1/film/" + filmwebId + "/rating");
        HttpCollector httpCollector1 = new HttpCollector(url1);
        httpCollector1.collect();
        if (!httpCollector1.getResponse().isEmpty()) {
            String response1 = String.valueOf(httpCollector1.getResponse());
            Gson gson1 = new Gson();
            RatingRequest ratingRequest = gson1.fromJson(response1, RatingRequest.class);
            Long filmwebIdAsLong = Long.parseLong(filmwebId);
            filmwebMovie.setId(filmwebIdAsLong);
            String ratingAsString = String.valueOf(ratingRequest.getRate());
            filmwebMovie.setRating(ratingAsString);
            String ratingCountAsString = String.valueOf(ratingRequest.getCount());
            filmwebMovie.setRatingCount(ratingCountAsString);
        }
        return filmwebMovie;
    }

    @Transactional
    public void collectProvidersInfo(boolean is_test) throws IOException {
        if (!is_test) {
            List<ProvidersInfo> providersInfoList = new ArrayList<>();
            URL url = new URL("https://www.filmweb.pl/api/v1/vod/providers/list");
            HttpCollector httpCollector = new HttpCollector(url);
            httpCollector.collect();
            if (!httpCollector.getResponse().isEmpty()) {
                String response = String.valueOf(httpCollector.getResponse());
                List<ProvidersRequest> providersList = ProvidersRequest.fromJsonArray(response);
                for (ProvidersRequest providerRequest : providersList) {
                    ProvidersInfo providersInfo = new ProvidersInfo(providerRequest.getId(), providerRequest.getName());
                    providersInfoList.add(providersInfo);
                }
                providersInfoRepository.saveAll(providersInfoList);
            }

        } else {
            System.out.println("collect providers dziala");
        }

    }

    @Transactional
    public void collectMovieProviders(String filmwebId) throws IOException {
        try {
            URL url = new URL("https://www.filmweb.pl/api/v1/vod/film/" + filmwebId + "/providers/list");
            HttpCollector httpCollector = new HttpCollector(url);
            httpCollector.collect();
            if (!httpCollector.getResponse().isEmpty()) {
                String response = String.valueOf(httpCollector.getResponse());
                List<MovieProvidersRequest> providersList = MovieProvidersRequest.fromJsonArray(response);
                for (MovieProvidersRequest movieProvidersRequest : providersList) {
                    MovieProvider movieProvider = new MovieProvider(findFilmwebMovie(filmwebId), findProvider(movieProvidersRequest));
                    movieProvidersRepository.save(movieProvider);
                }
            }

        } catch (Exception e) {
            logger.info("collectMovieProviders error");
            collectMovieProviders(filmwebId);
        }

    }

    public ProvidersInfo findProvider(MovieProvidersRequest movieProvidersRequest) {
        ProvidersInfo providersInfo1 = null;
        Long providerId = (long) movieProvidersRequest.getVodProvider();
        Optional<ProvidersInfo> providersInfo = providersInfoRepository.findById(providerId);
        if (providersInfo.isPresent()) {
            providersInfo1 = providersInfo.get();
        }
        return providersInfo1;
    }

    public FilmwebMovie findFilmwebMovie(String filmwebId) {
        FilmwebMovie filmwebMovie1 = null;
        Long filmwbIsAsLong = Long.valueOf(filmwebId);
        Optional<FilmwebMovie> filmwebMovie = filmwebMovieRepository.findById(filmwbIsAsLong);
        if (filmwebMovie.isPresent()) {
            filmwebMovie1 = filmwebMovie.get();
        }
        return filmwebMovie1;
    }
}