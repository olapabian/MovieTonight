package com.example.MovieTonight;

import com.example.MovieTonight.DataCollector.Filmweb.IdAndTitleCollector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieTonightApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieTonightApplication.class, args);
		//pobieranie id i tytulu do pliku
		IdAndTitleCollector collector = new IdAndTitleCollector();
		String result = collector.Collect();
		System.out.println(result);
	}

}
