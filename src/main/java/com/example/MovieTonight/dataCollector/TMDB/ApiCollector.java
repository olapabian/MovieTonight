package com.example.MovieTonight.dataCollector.TMDB;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ApiCollector {

    private OkHttpClient configureHttpClient() {
        Logger.getLogger(OkHttpClient.class.getName()).setLevel(Level.FINE);
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    private Request createHttpRequest(String url, String apiKey) {
        return new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();
    }


    private Response sendHttpRequest(OkHttpClient client, Request request) throws IOException {
        return client.newCall(request).execute();
    }

    public Response collect(String url) throws IOException {
        OkHttpClient client = configureHttpClient();
        String apiKey = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiNmM4ODAyMzBiNGQwNmFmOTEzZTBkYjMzNWRmZGRmNyIsInN1YiI6IjY1YmY3YTBhYmE0ODAyMDE2MTZhYTM1MiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.rKt_p6dsHDT4DpYwpzTcsghcfFYw1rG7PaMetM0R8Vk";
        Request request = createHttpRequest(url, apiKey);
        return sendHttpRequest(client, request);
    }
}
