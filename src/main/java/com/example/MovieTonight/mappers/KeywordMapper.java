package com.example.MovieTonight.mappers;

import com.example.MovieTonight.dataFromApi.KeywordCount;
import com.example.MovieTonight.model.database.KeywordPoints;
import com.example.MovieTonight.dataFromApi.KeywordStatistic;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class KeywordMapper {
    private KeywordStatistic keywordStatistic;
    private KeywordPoints keywordPoints;
    private KeywordCount keywordCount;

    public KeywordCount mapFromObjectToKeywordCount(Object[] object){

        KeywordCount keywordCount = new KeywordCount();
        keywordCount.setKeywordId((Long) object[0]);
        keywordCount.setGenreId((Long) object[1]);

        return keywordCount;
    }

    public List<KeywordPoints> mapFromKeywordStatisticListToKeywordPoints(Long keywordId, List<KeywordStatistic> keywordStatistic){

        List<KeywordPoints> resultList = new ArrayList<>();

        for(KeywordStatistic kS: keywordStatistic){
            KeywordPoints kp = new KeywordPoints();
            kp.setKeywordId(keywordId);
            kp.setGenreId(kS.getGenreId());
            kp.setPoints(kS.getOccurrences());

            resultList.add(kp);
        }

        return resultList;
    }

}
