package com.example.MovieTonight.services.interfaces;

import com.example.MovieTonight.dataFromApi.KeywordCount;

import java.util.List;

public interface KeywordPointsSerivce {

    public List<KeywordCount> getGenres(long keywordId);

    public void countGenresPointsForKeywords();


}
