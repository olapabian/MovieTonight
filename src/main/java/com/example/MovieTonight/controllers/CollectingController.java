package com.example.MovieTonight.controllers;

import com.example.MovieTonight.dataCollector.MainCollector;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CollectingController {
    private final MainCollector mainCollector;

    public CollectingController(MainCollector mainCollector) {
        this.mainCollector = mainCollector;
    }

    @GetMapping("/collect")
    public void collect() throws Exception {
        boolean collect_ids = true;
        boolean collect_filmweb = true;
        boolean collect_tmdb = false;
        mainCollector.collect(collect_ids, collect_filmweb, collect_tmdb);
    }
}
