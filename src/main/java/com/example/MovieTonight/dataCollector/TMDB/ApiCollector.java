package com.example.MovieTonight.dataCollector.TMDB;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ApiCollector {

    private final String apiKey = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiNmM4ODAyMzBiNGQwNmFmOTEzZTBkYjMzNWRmZGRmNyIsInN1YiI6IjY1YmY3YTBhYmE0ODAyMDE2MTZhYTM1MiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.rKt_p6dsHDT4DpYwpzTcsghcfFYw1rG7PaMetM0R8Vk"; // Token autoryzacyjny a nie api key

//    @PostConstruct
    public Response collect(String url) throws IOException {
        Logger.getLogger(OkHttpClient.class.getName()).setLevel(Level.FINE);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        Response response = client.newCall(request).execute();
//        System.out.println(response);
        return response;
    }
}
