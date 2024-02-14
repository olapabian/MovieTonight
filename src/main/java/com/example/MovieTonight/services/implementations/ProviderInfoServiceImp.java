package com.example.MovieTonight.services.implementations;

import com.example.MovieTonight.dataFromApi.KeywordCount;
import com.example.MovieTonight.model.database.KeywordPoints;
import com.example.MovieTonight.dataFromApi.KeywordStatistic;
import com.example.MovieTonight.mappers.KeywordMapper;
import com.example.MovieTonight.model.database.KeywordsInfo;
import com.example.MovieTonight.model.database.MovieGenre;
import com.example.MovieTonight.model.database.ProvidersInfo;
import com.example.MovieTonight.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@AllArgsConstructor
public class ProviderInfoServiceImp {
    //dorobicService
    ProvidersInfoRepository providersInfoRepository;
    TmdbMovieRepository tmdbMovieRepository;
    MovieGenresRepository movieGenresRepository;
    MovieKeywordRepository movieKeywordRepository;
    KeywordsInfoRepository keywordsInfoRepository;
    KeywordPointsRepository keywordPointsRepository;
    KeywordMapper keywordMapper;

    public List<ProvidersInfo> getAllProviders() {

        return providersInfoRepository.findAll();

    }

    public List<MovieGenre> getAllTmdbMovies() {

        return movieGenresRepository.findMovieGenreByTmdbId(550L);

    }

    public List<KeywordCount> getGenres(long keywordId) {

        List<Object[]> queryResults = movieKeywordRepository.countKeywordsPerEntity(keywordId);
        List<KeywordCount> keywordCounts = new ArrayList<>();

        for (Object[] result : queryResults) {
            KeywordCount keywordCount = keywordMapper.mapFromObjectToKeywordCount(result);
            keywordCounts.add(keywordCount);
        }


        return keywordCounts;
    }

    public void countGenresOccurrenceForKeywords() {
        List<KeywordsInfo> list = keywordsInfoRepository.findAll();
        List<Long> keywordIds = new ArrayList<>();
        for(KeywordsInfo keywordsInfo: list){
            keywordIds.add(keywordsInfo.getId());
        }

        List<KeywordPoints> resultList = new ArrayList<>();

        for (Long keywordId : keywordIds) {
            List<KeywordStatistic> keywordStatistic = new ArrayList<>();
            List<KeywordCount> keywordCounts = getGenres(keywordId);

            for (KeywordCount kcount : keywordCounts) {
                boolean found = false;
                for (KeywordStatistic ks : keywordStatistic) {
                    if (ks.getGenreId() == kcount.getGenreId()) {
                        ks.increaseOccurrences();
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    KeywordStatistic newStatistic = new KeywordStatistic();
                    newStatistic.setGenreId(kcount.getGenreId());
                    newStatistic.setOccurrences(1);
                    keywordStatistic.add(newStatistic);

                }

            }
            resultList.addAll(keywordMapper.mapFromKeywordStatisticListToKeywordPoints(keywordId, keywordStatistic));
        }
        keywordPointsRepository.saveAll(resultList);

    }

}