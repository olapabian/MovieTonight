package com.example.MovieTonight.DataCollector.TMDB;

import com.example.MovieTonight.JSONs.TMDB.GsonMovie;
import com.example.MovieTonight.JSONs.TMDB.KeywordsRequest;
import com.example.MovieTonight.JSONs.TMDB.MovieDetails;
import com.example.MovieTonight.JSONs.TMDB.MovieSearchResponse;
import com.example.MovieTonight.Model.database.*;
import com.example.MovieTonight.Model.others.TmdbMovieAndMovie;
import com.example.MovieTonight.Repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.google.gson.Gson;

import java.util.ArrayList;

@AllArgsConstructor
@Service
public class TMDBCollector {
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

    //    @PostConstruct
    public void collect(String filePath, String filePath2, String filePath3) {
        //leciec dopuki nie znajdzie danego id
//        int ile = 0;
//        boolean isStart=false; //jak mi sie przerwie i wtedy ustwaiam tam dalej jaki id
        boolean isStart = true; //za pierwszym razme
        try (BufferedReader br = new BufferedReader(new FileReader(filePath));
             BufferedReader br2 = new BufferedReader(new FileReader(filePath2));
             BufferedReader br3 = new BufferedReader(new FileReader(filePath3))) {
            String line;
            String year;
            String filmwebId;
            List<TmdbMovie> tmdbMovieList = new ArrayList<>();
            List<Movie> movieList = new ArrayList<>();
            while ((line = br.readLine()) != null && (year = br2.readLine()) != null && (filmwebId = br3.readLine()) != null) {
                System.out.println(filmwebId);
                if (isStart) {
                    Movie movie = saveTmdb(line, year, filmwebId); //tabela filmwebMovies

                    //dodaje do list
                    if (movie != null) {
                        movieList.add(movie);
                    }
                    System.out.println(line);
                }
//                if(filmwebId.equals("1"))
//                {
//                    isStart=true;
//                }
//                ile++;
//                System.out.println(ile);
            }
            movieRepository.saveAll(movieList);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }
        System.out.println("pobrano dane TMDB" + filePath3);
    }

    @Transactional
    public Movie saveTmdb(String title, String year, String filmwebId) throws IOException {
        TmdbMovieAndMovie tmdbMovieAndMovie = new TmdbMovieAndMovie();
        Movie movie = null;
        try {

            Optional<FilmwebMovie> filmwebMovie = filmwebMovieRepository.findById(Long.parseLong(filmwebId));

            if (filmwebMovie.isPresent()) {
                FilmwebMovie filmwebMovie1 = filmwebMovie.get();
                movie = movieRepository.findByFilmweb(filmwebMovie1);
                TmdbMovie tmdbMovie = new TmdbMovie();
                tmdbMovie.setId(1L);

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
                                if (gsonMovie.getOriginalTitle().equals(title) && gsonMovie.getReleaseDate().substring(0, 4).equals(year)) {
                                    //TMDB Id
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
                response.body().close();


                System.out.println("znaleziono film");
                //Request with many informations
                String url1 = "https://api.themoviedb.org/3/movie/" + String.valueOf(tmdbMovie.getId()) + "?append_to_response=credits&language=pl-PL";
                ApiCollector apiCollector1 = new ApiCollector();
                Response response1 = apiCollector1.collect(url1);
                if (response1.isSuccessful()) {
                    String jsonResponse1 = response1.body().string();
                    if (jsonResponse1 != null && !jsonResponse1.isEmpty()) {
                        Gson gson = new Gson();
                        MovieDetails movieDetails = gson.fromJson(jsonResponse1, MovieDetails.class);

                        //Description
                        tmdbMovie.setDescription(movieDetails.getOverview());

//                //ProductionCountries np "United States of America;Singapore;Mexico;"
                        String countries = "";
                        for (int i = 0; movieDetails.getProductionCountries().size() > i; i++) {
                            countries += movieDetails.getProductionCountries().get(i).getName() + ";";
                        }
                        tmdbMovie.setProductionCountries(countries);

                        //Runtime tmdbMovie
                        tmdbMovie.setRuntime((long) movieDetails.getRuntime());

                        //Popularity tmdbMovie
                        tmdbMovie.setPopularity(movieDetails.getPopularity());

                        //tu jest SAVE
                        tmdbMovieRepository.save(tmdbMovie);


                        //Pętla po crew (zapisywanie rezystero
                        for (int i = 0; i < movieDetails.getCredits().getCrew().size(); ++i) {
//                    System.out.println(movieDetails.getCredits().getCrew().get(i).getKnownForDepartment());
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

                        //Pętla po cast ( zapisywanie  aktorów)
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

                            }
                        }

                        System.out.println("pobrano aktorow");

                        //Pętla po genres zapisywanie wszystkich gatunkto pojedynczo

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
                }
                response1.body().close();

                //Zapisywanie
                String url4 = "https://api.themoviedb.org/3/movie/" + String.valueOf(tmdbMovie.getId()) + "/keywords";
                ApiCollector apiCollector4 = new ApiCollector();
                Response response4 = apiCollector4.collect(url4);
                if (response4.isSuccessful()) {
                    String jsonResponse1 = response4.body().string();
                    if (jsonResponse1 != null && !jsonResponse1.isEmpty()) {
                        Gson gson = new Gson();
                        KeywordsRequest keywordsRequest = gson.fromJson(jsonResponse1, KeywordsRequest.class);

                        List<KeywordsInfo> keywordsInfoList = new ArrayList<>();
                        List<MovieKeyword> movieKeywordList = new ArrayList<>();
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
                response4.body().close();
                movie.setTmdb(tmdbMovie);

                //i tu jest SAVE
//                    movieRepository.save(movie);

            }

        } catch (Exception e) {
            System.out.println("wylapalo wyjatek" + e.getMessage());
            saveTmdb(title, year, filmwebId);
        }
        return movie;
    }
}



