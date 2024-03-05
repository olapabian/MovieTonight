package com.example.MovieTonight;

import com.example.MovieTonight.DataCollector.Filmweb.FilmwebCollector;
import com.example.MovieTonight.DataCollector.TMDB.TMDBCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieTonightApplication implements CommandLineRunner {

	@Autowired
	private FilmwebCollector filmwebCollector;
	@Autowired
	private TMDBCollector tmdbCollector;

	public static void main(String[] args) {
		SpringApplication.run(MovieTonightApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//Najpierw pobieranie providers list
//		filmwebCollector.collectProvidersInfo(); //lista wszystkich providers
		// Utwórz i uruchom 10 wątków
//		for (int i = 0; i < 9; i++) {
//			String filePath = "filmwebIDs" + i + ".txt";
//			Thread thread = new Thread(() -> {
//				try {
//					filmwebCollector.filmwebCollect(filePath);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			});
//			thread.start();
//		}

		// Utwórz i uruchom 10 wątków tmdb
//		for (int i = 0; i < 9; i++) {
//			String filePath = "titles" + i + ".txt";
//			String filePath2 = "years" + i + ".txt";
//			String filePath3 = "filmwebIDs" + i + ".txt";
//			Thread thread = new Thread(() -> {
//				try {
//					tmdbCollector.collect(filePath, filePath2, filePath3);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			});
//			thread.start();
//		}
	}
}
