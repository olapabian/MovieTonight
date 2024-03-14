package com.example.MovieTonight.controllers;

import com.example.MovieTonight.DataCollector.MainCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CollectingController {
    @Autowired
    private MainCollector mainCollector;

    @GetMapping("/collect")
    public void collect() throws Exception {
        boolean collect_ids = true;
        boolean collect_filmweb = true;
        boolean collect_tmdb = true;
        mainCollector.collect(collect_ids, collect_filmweb, collect_tmdb);
    }
}
