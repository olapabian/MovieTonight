package com.example.MovieTonight.dataCollector.TMDB;

import com.example.MovieTonight.JSONs.TMDB.GsonMovie;
import com.example.MovieTonight.JSONs.TMDB.KeywordsRequest;
import com.example.MovieTonight.JSONs.TMDB.MovieDetails;
import com.example.MovieTonight.JSONs.TMDB.MovieSearchResponse;
import com.example.MovieTonight.Model.database.*;
import com.example.MovieTonight.Repository.*;
import com.example.MovieTonight.dataCollector.filmweb.FilmwebCollector;
import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TMDBCollector {
    private static final Logger logger = LoggerFactory.getLogger(FilmwebCollector.class);
    private final TmdbMovieRepository tmdbMovieRepository;
    private final MovieRepository movieRepository;
    private final MovieDirectorsRepository movieDirectorsRepository;
    private final DirectorsInfoRepository directorsInfoRepository;
    private final ActorsInfoRepository actorsInfoRepository;
    private final MovieActorsRepository movieActorsRepository;
    private final MovieGenresRepository movieGenresRepository;
    private final GenresInfoRepository genresInfoRepository;
    private final MovieKeywordRepository movieKeywordRepository;
    private final KeywordsInfoRepository keywordsInfoRepository;
    private final FilmwebMovieRepository filmwebMovieRepository;

    public void collect(String filePath, String filePath2, String filePath3, boolean is_test, int startId) {
        if (!is_test) {
            collectData(filePath, filePath2, filePath3, startId);
        } else {
            System.out.println("tmdb dzila");
        }
    }

    private void collectData(String filePath, String filePath2, String filePath3, int startId) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath));
             BufferedReader br2 = new BufferedReader(new FileReader(filePath2));
             BufferedReader br3 = new BufferedReader(new FileReader(filePath3))) {
            int row = 0;
            boolean isStart = startId == 0;
            List<Movie> movieList = new ArrayList<>();
            String line;
            String year;
            String filmwebId;
            while ((line = br.readLine()) != null && (year = br2.readLine()) != null && (filmwebId = br3.readLine()) != null) {
                row++;
                if (isStart) {
                    Movie movie = saveTmdb(line, year, filmwebId);
                    if (movie != null) {
                        movieList.add(movie);
                    }
                }
                if (row == startId) {
                    isStart = true;
                }
            }
            movieRepository.saveAll(movieList);
            System.out.println("Data from TMDB collected from file: " + filePath3);
        } catch (IOException e) {
            logger.info("collectData");
        }
    }

    @Transactional
    public Movie saveTmdb(String title, String year, String filmwebId) throws IOException {
        Movie movie = new Movie();
        Optional<FilmwebMovie> filmwebMovie = filmwebMovieRepository.findById(Long.parseLong(filmwebId));
        if (filmwebMovie.isPresent()) {
            FilmwebMovie filmwebMovie1 = filmwebMovie.get();
            movie = movieRepository.findByFilmweb(filmwebMovie1);
            TmdbMovie tmdbMovie = findTmdbMovieIdAndAdultFlag(title, year);
            MovieDetails movieDetails = saveDecsriptionProductionCountriesRuntimeAndPopularityFromTmdb(tmdbMovie);
            saveDirectors(movieDetails, tmdbMovie);
            saveCast(movieDetails, tmdbMovie);
            saveGenres(movieDetails, tmdbMovie);
            saveKeywords(tmdbMovie);
            movie.setTmdb(tmdbMovie);

        }
        return movie;
    }

    private void saveKeywords(TmdbMovie tmdbMovie) throws IOException {
        String url4 = "https://api.themoviedb.org/3/movie/" + tmdbMovie.getId() + "/keywords";
        ApiCollector apiCollector4 = new ApiCollector();
        Response response4 = apiCollector4.collect(url4);
        if (response4.isSuccessful()) {
            assert response4.body() != null;
            String jsonResponse1 = response4.body().string();
            if (!jsonResponse1.isEmpty()) {
                Gson gson = new Gson();
                KeywordsRequest keywordsRequest = gson.fromJson(jsonResponse1, KeywordsRequest.class);
                for (int i = 0; i < keywordsRequest.getKeywords().size(); ++i) {
                    KeywordsInfo keywordsInfo = new KeywordsInfo();
                    keywordsInfo.setId(keywordsRequest.getKeywords().get(i).getId());
                    keywordsInfo.setName(keywordsRequest.getKeywords().get(i).getName());
                    keywordsInfoRepository.save(keywordsInfo);
                    MovieKeyword movieKeyword = new MovieKeyword();
                    movieKeyword.setTmdb(tmdbMovie);
                    movieKeyword.setKeyword(keywordsInfo);
                    movieKeywordRepository.save(movieKeyword);
                }
            }
        }
        System.out.println("pobrano keywords");
        assert response4.body() != null;
        response4.body().close();
    }

    private void saveGenres(MovieDetails movieDetails, TmdbMovie tmdbMovie) {
        for (int i = 0; i < movieDetails.getGenres().size(); ++i) {
            GenresInfo genresInfo = new GenresInfo();
            genresInfo.setId(movieDetails.getGenres().get(i).getId());
            genresInfo.setName(movieDetails.getGenres().get(i).getName());
            MovieGenre movieGenre = new MovieGenre();
            movieGenre.setGenre(genresInfo);
            movieGenre.setTmdbMovie(tmdbMovie);
            genresInfoRepository.save(genresInfo);
            movieGenresRepository.save(movieGenre);
        }
        System.out.println("pobrano gatunki");
    }

    private void saveCast(MovieDetails movieDetails, TmdbMovie tmdbMovie) {
        for (int i = 0; i < movieDetails.getCredits().getCast().size(); ++i) {
            if (movieDetails.getCredits().getCast().get(i).getKnownForDepartment().equals("Acting")) {
                ActorsInfo actorsInfo = new ActorsInfo();
                actorsInfo.setId(movieDetails.getCredits().getCast().get(i).getId());
                actorsInfo.setFullName(movieDetails.getCredits().getCast().get(i).getName());
                MovieActor movieActor = new MovieActor();
                movieActor.setTmdbMovie(tmdbMovie);
                movieActor.setActor(actorsInfo);
                actorsInfoRepository.save(actorsInfo);
                movieActorsRepository.save(movieActor);
                System.out.println("pobrano aktorow");
            }
        }
    }

    private void saveDirectors(MovieDetails movieDetails, TmdbMovie tmdbMovie) {
        for (int i = 0; i < movieDetails.getCredits().getCrew().size(); ++i) {
            if (movieDetails.getCredits().getCrew().get(i).getDepartment().equals("Directing") && movieDetails.getCredits().getCrew().get(i).getKnownForDepartment().equals("Directing")) {
                DirectorsInfo directorsInfo = new DirectorsInfo();
                directorsInfo.setId((long) movieDetails.getCredits().getCrew().get(i).getId());
                directorsInfo.setFullName(movieDetails.getCredits().getCrew().get(i).getName());
                MovieDirector movieDirector = new MovieDirector();
                movieDirector.setTmdbMovie(tmdbMovie);
                movieDirector.setDirector(directorsInfo);
                directorsInfoRepository.save(directorsInfo);
                movieDirectorsRepository.save(movieDirector);
            }
        }
        System.out.println("pobrano rezyserow");
    }


    private MovieDetails saveDecsriptionProductionCountriesRuntimeAndPopularityFromTmdb(TmdbMovie tmdbMovie) throws IOException {
        String url1 = "https://api.themoviedb.org/3/movie/" + tmdbMovie.getId() + "?append_to_response=credits&language=pl-PL";
        ApiCollector apiCollector1 = new ApiCollector();
        Response response1 = apiCollector1.collect(url1);
        MovieDetails movieDetails = null;
        if (response1.isSuccessful()) {
            assert response1.body() != null;
            String jsonResponse1 = response1.body().string();
            if (!jsonResponse1.isEmpty()) {
                Gson gson = new Gson();
                movieDetails = gson.fromJson(jsonResponse1, MovieDetails.class);
                tmdbMovie.setDescription(movieDetails.getOverview());
                StringBuilder countries = new StringBuilder();
                for (int i = 0; movieDetails.getProductionCountries().size() > i; i++) {
                    countries.append(movieDetails.getProductionCountries().get(i).getName()).append(";");
                }
                tmdbMovie.setProductionCountries(countries.toString());
                tmdbMovie.setRuntime((long) movieDetails.getRuntime());
                tmdbMovie.setPopularity(movieDetails.getPopularity());
                tmdbMovieRepository.save(tmdbMovie);
            }
        }
        assert response1.body() != null;
        response1.body().close();
        return movieDetails;
    }

    private TmdbMovie findTmdbMovieIdAndAdultFlag(String title, String year) throws IOException {
        TmdbMovie tmdbMovie = new TmdbMovie();
        tmdbMovie.setId(1L);

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
                        if (gsonMovie.getOriginalTitle().equals(title) && gsonMovie.getReleaseDate().substring(0, 4).equals(year)) {
                            tmdbMovie.setId(gsonMovie.getId());
                            System.out.println(gsonMovie.getTitle());
                            tmdbMovie.setAdult(gsonMovie.isAdult());
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
        assert response.body() != null;
        response.body().close();


        System.out.println("znaleziono film");
        return tmdbMovie;
    }
}




