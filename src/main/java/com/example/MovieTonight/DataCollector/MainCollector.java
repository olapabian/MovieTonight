package com.example.MovieTonight.DataCollector;

import com.example.MovieTonight.DataCollector.Filmweb.FilmwebCollector;
import com.example.MovieTonight.DataCollector.Filmweb.IdAndTitleCollector;
import com.example.MovieTonight.DataCollector.TMDB.TMDBCollector;
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
        if (collect_ids) {
            boolean is_test = true;
            idAndTitleCollector.collect(is_test);
        }
        if (collect_filmweb) {
            boolean is_test = true;

            //Najpierw pobieranie providers list
            filmwebCollector.collectProvidersInfo(is_test); //lista wszystkich providers

            List<Thread> threads = new ArrayList<>();

            // Utwórz i uruchom 10 wątków
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
                threads.add(thread); // Dodaj wątek do listy
            }

            // Poczekaj na zakończenie wszystkich wątków
            for (Thread thread : threads) {
                thread.join();
            }

            System.out.println("Wszystkie wątki zakończone.");
        }
        if (collect_tmdb) {
            boolean is_test = true;

            List<Thread> threads = new ArrayList<>();
            // Create and start 4 threads for the first file
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

            // Utwórz i uruchom 10 wątków tmdb
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

            // Informacja o zakończeniu wszystkich wątków
            System.out.println("Wszystkie wątki zakończone.");
        }
    }
}
