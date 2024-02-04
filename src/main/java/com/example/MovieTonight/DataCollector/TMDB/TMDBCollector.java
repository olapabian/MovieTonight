package com.example.MovieTonight.DataCollector.TMDB;

import com.example.MovieTonight.JSONs.TMDB.GsonMovie;
import com.example.MovieTonight.JSONs.TMDB.MovieDetails;
import com.example.MovieTonight.JSONs.TMDB.MovieSearchResponse;
import com.example.MovieTonight.Model.Movie;
import com.example.MovieTonight.Model.TmdbMovie;
import com.example.MovieTonight.Repository.MovieRepository;
import com.example.MovieTonight.Repository.TmdbMovieRepository;
import com.google.gson.JsonObject;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import com.google.gson.Gson;

@AllArgsConstructor
@Service
public class TMDBCollector {
    private final TmdbMovieRepository tmdbMovieRepository;

    @PostConstruct
    public void collect() throws FileNotFoundException {
        String filePath = "titles.txt";
        String filePath2 = "years.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath));
        BufferedReader br2 = new BufferedReader(new FileReader(filePath2))) {
            String line;
            String year;
            while ((line = br.readLine()) != null && (year = br2.readLine()) != null) {
                saveTmdbId(line,year); //movie and filmwebMovies
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }
        System.out.println("pobrano dane TMDB");
    }
    public void saveTmdbId(String title, String year) throws IOException {
        TmdbMovie tmdbMovie = new TmdbMovie();

        //Request search
        String url ="https://api.themoviedb.org/3/search/movie?api_key=&query=" + title;
        ApiCollector apiCollector = new ApiCollector();
        Response response = apiCollector.collect(url);
        if (response.isSuccessful()) {
            String jsonResponse = response.body().string();

            if (jsonResponse != null && !jsonResponse.isEmpty()) {
                Gson gson = new Gson();
                MovieSearchResponse movieSearchResponse = gson.fromJson(jsonResponse, MovieSearchResponse.class);

                //petla po liscie wynikow
                for (int i = 0; i < movieSearchResponse.getResults().size(); ++i) {
                    GsonMovie gsonMovie = movieSearchResponse.getResults().get(i);
                    if(gsonMovie.getReleaseDate().length() >=4)
                    {
                        if (gsonMovie.getTitle().equals(title) && gsonMovie.getReleaseDate().substring(0,4).equals(year)) {
                            //TMDB Id
                            tmdbMovie.setId(gsonMovie.getId());
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

        //Request with many informations
        String url1 ="https://api.themoviedb.org/3/movie/" + String.valueOf(tmdbMovie.getId()) + "?append_to_response=credits&language=pl-PL" ;
        ApiCollector apiCollector1 = new ApiCollector();
        Response response1 = apiCollector1.collect(url1);
        if (response1.isSuccessful()) {
            String jsonResponse1 = response1.body().string();  // Poprawka tutaj
            if (jsonResponse1 != null && !jsonResponse1.isEmpty()) {
                Gson gson = new Gson();
                MovieDetails movieDetails = gson.fromJson(jsonResponse1,MovieDetails.class);

                //Description
                tmdbMovie.setDescription(movieDetails.getOverview());
                System.out.println(tmdbMovie.getDescription());

//                //ProductionCountries
//                tmdbMovie.setProductionCountries(String.valueOf(movieDetails.getProductionCountries()));
//                System.out.println(tmdbMovie.getProductionCountries());
            }
        }
    }

}
