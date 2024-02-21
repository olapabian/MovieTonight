package com.example.MovieTonight.mappers;

import com.example.MovieTonight.dataFromApi.KeywordCount;
import com.example.MovieTonight.model.database.KeywordPoints;
import com.example.MovieTonight.dataFromApi.KeywordStatistic;
import com.example.MovieTonight.model.database.KeywordsInfo;
import com.example.MovieTonight.repository.KeywordsInfoRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class KeywordMapper {

    KeywordsInfoRepository keywordsInfoRepository;
    private KeywordStatistic keywordStatistic;
    private KeywordPoints keywordPoints;
    private KeywordCount keywordCount;

    public KeywordCount mapFromObjectToKeywordCount(Object[] object){

        KeywordCount keywordCount = new KeywordCount();
        keywordCount.setKeywordId((Long) object[0]);
        keywordCount.setGenreId((Long) object[1]);

        return keywordCount;
    }

}
