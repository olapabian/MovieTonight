package com.example.MovieTonight.dataCollector.filmweb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HttpCollector {
    URL url;

    private StringBuilder response = new StringBuilder();
    public void collect() throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("x-locale", "pl-PL");
        con.setReadTimeout(5000);
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        }
    }

    public byte[] getResponseBytes() throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("x-locale", "pl-PL");
        con.setReadTimeout(5000);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (InputStream inputStream = con.getInputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        return outputStream.toByteArray();
    }

    public HttpCollector(URL url) {
        this.url = url;
    }

}