package com.example.MovieTonight;

import com.example.MovieTonight.DataCollector.Filmweb.FilmwebCollector;
import com.example.MovieTonight.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieTonightApplication {


	public static void main(String[] args) {

		// Run the Spring application
		SpringApplication.run(MovieTonightApplication.class, args);


	}
}
