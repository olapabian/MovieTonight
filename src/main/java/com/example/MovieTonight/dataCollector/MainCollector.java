package com.example.MovieTonight.dataCollector;

import com.example.MovieTonight.dataCollector.TMDB.TMDBCollector;
import com.example.MovieTonight.dataCollector.filmweb.FilmwebCollector;
import com.example.MovieTonight.dataCollector.filmweb.IdAndTitleCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainCollector {
    @Autowired
    private IdAndTitleCollector idAndTitleCollector;
    @Autowired
    private FilmwebCollector filmwebCollector;
    @Autowired
    private TMDBCollector tmdbCollector;
    public void collect(boolean collect_ids, boolean collect_filmweb, boolean collect_tmdb) throws Exception {
        boolean is_test = false;
        if (collect_ids) {
            idAndTitleCollector.collect(is_test);
        }
        if (collect_filmweb) {
            filmwebCollector.collectProvidersInfo(is_test);
            List<Thread> threads = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                String filePath = "filmwebIDs" + i + ".txt";
                Thread thread = new Thread(() -> {
                    try {
                        filmwebCollector.filmwebCollect(filePath, is_test);
                    } catch (Exception e) {
                        e.printStackTrace();
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
        if (collect_tmdb) {

            List<Thread> threads = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                String filePath = "titles0.txt";
                String filePath2 = "years0.txt";
                String filePath3 = "filmwebIDs0.txt";
                final int startId = i * 1750;
                Thread thread = new Thread(() -> {
                    try {
                        tmdbCollector.collect(filePath, filePath2, filePath3, is_test, startId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                thread.start();
                threads.add(thread);
            }
            for (int i = 1; i < 9; i++) {
                String filePath = "titles" + i + ".txt";
                String filePath2 = "years" + i + ".txt";
                String filePath3 = "filmwebIDs" + i + ".txt";
                Thread thread = new Thread(() -> {
                    try {
                        tmdbCollector.collect(filePath, filePath2, filePath3, is_test, 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                thread.start();
            }

            for (Thread thread : threads) {
                thread.join();
            }
            System.out.println("Wszystkie wątki zakończone.");
        }
    }
}
