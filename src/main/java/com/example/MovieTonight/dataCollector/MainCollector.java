package com.example.MovieTonight.dataCollector;

import com.example.MovieTonight.dataCollector.filmweb.FilmwebCollector;
import com.example.MovieTonight.dataCollector.filmweb.IdAndTitleCollector;
import com.example.MovieTonight.dataCollector.tmdb.TMDBCollector;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class MainCollector {
    private final IdAndTitleCollector idAndTitleCollector;
    private final FilmwebCollector filmwebCollector;
    private final TMDBCollector tmdbCollector;

    public MainCollector(IdAndTitleCollector idAndTitleCollector, FilmwebCollector filmwebCollector, TMDBCollector tmdbCollector) {
        this.idAndTitleCollector = idAndTitleCollector;
        this.filmwebCollector = filmwebCollector;
        this.tmdbCollector = tmdbCollector;
    }

    public void collect(boolean collect_ids, boolean collect_filmweb, boolean collect_tmdb) throws Exception {
        boolean is_test = false;
        if (collect_ids) {
            collectIds(is_test);
        }
        if (collect_filmweb) {
            collectFilmwebData(is_test);
        }
        if (collect_tmdb) {
            collectTmdbData(is_test);
        }
    }

    private void collectIds(boolean is_test) {
        idAndTitleCollector.collect(is_test);
    }

    private void collectFilmwebData(boolean is_test) throws InterruptedException, IOException {
        filmwebCollector.collectProvidersInfo(is_test);
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            String filePath = "filmwebIDs" + i + ".txt";
            Thread thread = new Thread(() -> {
                try {
                    filmwebCollector.filmwebCollect(filePath, is_test);
                } catch (Exception e) {
                    System.out.println("collectFilmwebData");
                }
            });
            thread.start();
            threads.add(thread);
        }
        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println("Wszystkie wątki zakończone.");
    }

    private void collectTmdbData(boolean is_test) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        IntStream.range(0, 4).forEach(i -> {
            String filePath = "titles0.txt";
            String filePath2 = "years0.txt";
            String filePath3 = "filmwebIDs0.txt";
            final int startId = i * 1750;
            Thread thread = new Thread(() -> {
                try {
                    tmdbCollector.collect(filePath, filePath2, filePath3, is_test, startId);
                } catch (Exception e) {
                    System.out.println("collectTmdbData");
                }
            });
            thread.start();
            threads.add(thread);
        });
        IntStream.range(1, 9).forEach(i -> {
            String filePath = "titles" + i + ".txt";
            String filePath2 = "years" + i + ".txt";
            String filePath3 = "filmwebIDs" + i + ".txt";
            try {
                tmdbCollector.collect(filePath, filePath2, filePath3, is_test, 0);
            } catch (Exception e) {
                System.out.println("collectTmdbData");
            }
        });

        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println("Wszystkie wątki zakończone.");
    }

}
