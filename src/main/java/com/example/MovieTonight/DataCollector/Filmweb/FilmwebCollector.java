package com.example.MovieTonight.DataCollector.Filmweb;

import com.example.MovieTonight.JSONs.Filmweb.InfoRequest;
import com.example.MovieTonight.JSONs.Filmweb.MovieProvidersRequest;
import com.example.MovieTonight.JSONs.Filmweb.ProvidersRequest;
import com.example.MovieTonight.JSONs.Filmweb.RatingRequest;
import com.example.MovieTonight.Model.database.FilmwebMovie;
import com.example.MovieTonight.Model.database.Movie;
import com.example.MovieTonight.Model.database.MovieProvider;
import com.example.MovieTonight.Model.database.ProvidersInfo;
import com.example.MovieTonight.Model.others.FilmwebMovieAndMovie;
import com.example.MovieTonight.Repository.FilmwebMovieRepository;
import com.example.MovieTonight.Repository.MovieProvidersRepository;
import com.example.MovieTonight.Repository.MovieRepository;
import com.example.MovieTonight.Repository.ProvidersInfoRepository;
import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
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
    private final MovieRepository movieRepository;
    private final FilmwebMovieRepository filmwebMovieRepository;
    private final ProvidersInfoRepository providersInfoRepository;
    private final MovieProvidersRepository movieProvidersRepository;
//    @PostConstruct // sie uruchomi po starcie apki

    public void filmwebCollect(String filePath ) throws IOException {


        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            List<Movie> movieList = new ArrayList<>();
            List<FilmwebMovie> filmwebMovieList = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                //wywolanie funkjci ktora zwraca obiekt
                FilmwebMovieAndMovie filmwebMovieAndMovie = collectMovie(line); //movie and filmwebMovies

                //dodanie do listy filmwebmovie
                filmwebMovieList.add(filmwebMovieAndMovie.getFilmwebMovie());

                //dodanie do listy movie
                movieList.add(filmwebMovieAndMovie.getMovie());

            }
            //zapisanie wszystkich filmow
            filmwebMovieRepository.saveAll(filmwebMovieList);
            movieRepository.saveAll(movieList);

        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }

        //pobranie polaczen z providers
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                collectMovieProviders(line); //dla kazdego filmu liste providers
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }

        System.out.println("pobrano dane " + filePath);
    }
    @Transactional
    public FilmwebMovieAndMovie collectMovie(String filmwebId) throws IOException {
        FilmwebMovieAndMovie filmwebMovieAndMovie = null;
        try {
            filmwebMovieAndMovie = new FilmwebMovieAndMovie();
            //Zmienne pomocnicze
            Movie movie = new Movie();
            FilmwebMovie filmwebMovie = new FilmwebMovie();

            URL url = new URL("https://www.filmweb.pl/api/v1/film/" + filmwebId + "/info");
            HttpCollector httpCollector = new HttpCollector(url);
            httpCollector.Collect();
            if (!httpCollector.getResponse().isEmpty()) {
                String response = String.valueOf(httpCollector.getResponse());
                Gson gson = new Gson();
                InfoRequest infoRequest = gson.fromJson(response, InfoRequest.class);
                System.out.println("filmwebID" + filmwebId);
                //title to movie
                String titleAsString = String.valueOf(infoRequest.getTitle());
                movie.setTitle(titleAsString);

                // original title to movie
                String originalTitleAsString = null;
                if (infoRequest.getOriginalTitle() != null && !infoRequest.getOriginalTitle().isEmpty()) {
                    originalTitleAsString = String.valueOf(infoRequest.getOriginalTitle());
                } else originalTitleAsString = String.valueOf(infoRequest.getTitle());
                movie.setOriginalTitle(originalTitleAsString);

                // release date
                Long releaseDateAsLong = Long.valueOf(infoRequest.getYear());
                filmwebMovie.setReleaseDate(releaseDateAsLong);
                System.out.println("pobrano info" + infoRequest.getTitle());
            }

            URL url1 = new URL("https://www.filmweb.pl/api/v1/film/" + filmwebId + "/rating");
            HttpCollector httpCollector1 = new HttpCollector(url1);
            httpCollector1.Collect();
            if (!httpCollector1.getResponse().isEmpty()) {
                String response1 = String.valueOf(httpCollector1.getResponse());
                Gson gson1 = new Gson();
                RatingRequest ratingRequest = gson1.fromJson(response1, RatingRequest.class);

                //filmweb_id to filmwebMovie
                Long filmwebIdAsLong = Long.parseLong(filmwebId);
                filmwebMovie.setId(filmwebIdAsLong);

                // rating to filmwebMovie
                String ratingAsString = String.valueOf(ratingRequest.getRate());
                filmwebMovie.setRating(ratingAsString);

                // rating count to filmwebMovie
                String ratingCountAsString = String.valueOf(ratingRequest.getCount());
                filmwebMovie.setRatingCount(ratingCountAsString);
            }
            movie.setFilmweb(filmwebMovie);
            //zapis do bazy
            filmwebMovieAndMovie.setFilmwebMovie(filmwebMovie);
            filmwebMovieAndMovie.setMovie(movie);

//            filmwebMovieRepository.save(filmwebMovie);
//            movieRepository.save(movie);
        } catch (Exception e) {
            System.out.println(e);
            collectMovie(filmwebId);
        }
        System.out.println("pobrano rating");
        return filmwebMovieAndMovie;

    }
    @Transactional
    public void collectProvidersInfo() throws IOException {
        try{
            List<ProvidersInfo> providersInfoList = new ArrayList<>();
            URL url = new URL("https://www.filmweb.pl/api/v1/vod/providers/list");
            HttpCollector httpCollector = new HttpCollector(url);
            httpCollector.Collect();
            if(!httpCollector.getResponse().isEmpty()){
                String response = String.valueOf(httpCollector.getResponse());
                List<ProvidersRequest> providersList = ProvidersRequest.fromJsonArray(response);
                for (ProvidersRequest providerRequest : providersList) {
                    ProvidersInfo providersInfo = new ProvidersInfo();
                    // Ustawianie danych
                    providersInfo.setId((long) providerRequest.getId());
                    providersInfo.setName(providerRequest.getName());
                    //dodoaje do listy
                    providersInfoList.add(providersInfo);
                }
                providersInfoRepository.saveAll(providersInfoList);
            }
        }catch (Exception e)
        {
            System.out.println(e);
            collectProvidersInfo();
        }

    }
    @Transactional
    public void collectMovieProviders(String filmwebId) throws IOException {
        try {
            URL url = new URL("https://www.filmweb.pl/api/v1/vod/film/" + filmwebId + "/providers/list");
            HttpCollector httpCollector = new HttpCollector(url);
            httpCollector.Collect();
            if(!httpCollector.getResponse().isEmpty()){
                String response = String.valueOf(httpCollector.getResponse());
                List<MovieProvidersRequest> providersList = MovieProvidersRequest.fromJsonArray(response);

                for(MovieProvidersRequest movieProvidersRequest : providersList){
                    MovieProvider movieProvider = new MovieProvider();

                    //Ustawienie providera
                    Long providerId = Long.valueOf(movieProvidersRequest.getVodProvider());
                    Optional<ProvidersInfo> providersInfo = providersInfoRepository.findById(providerId);
                    if(providersInfo.isPresent()){
                        ProvidersInfo providersInfo1 = providersInfo.get();
                        movieProvider.setProvider(providersInfo1);
                    }

                    //Ustawienie filmwebMovie
                    Long filmwbIsAsLong = Long.valueOf(filmwebId);
                    Optional<FilmwebMovie> filmwebMovie = filmwebMovieRepository.findById(filmwbIsAsLong);
                    if(filmwebMovie.isPresent()){
                        FilmwebMovie filmwebMovie1 = filmwebMovie.get();
                        movieProvider.setFilmweb(filmwebMovie1);
                    }
                    movieProvidersRepository.save(movieProvider);

                }
            }

        } catch(Exception e)
        {
            System.out.println(e);
            collectMovieProviders(filmwebId);
        }

    }


}
