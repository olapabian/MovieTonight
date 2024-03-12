package com.example.MovieTonight.services;

import com.example.MovieTonight.algorithm.*;
import com.example.MovieTonight.model.Question;
import com.example.MovieTonight.model.QuizResults;
import com.example.MovieTonight.repository.QuestionAlgorithmRepository;
import com.example.MovieTonight.repository.QuizResultsRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Getter
@AllArgsConstructor
public class QuestionAlgorithmImp {
    QuestionAlgorithmRepository questionAlgorithmRepository;
    QuizResultsRepository quizResultsRepository;
    AlgorithmServiceImp algorithmServiceImp;

    public QuestionsResponse getStartingQuestions(){
        List<QuestionForQuiz> startingQuestions = new ArrayList<>();
        Long quizId = questionAlgorithmRepository.getQuizId();
        QuizResults quizResults = new QuizResults();
        quizResults.setQuizId(quizId);
        quizResultsRepository.save(quizResults);

        List<String> sideAnswers = new ArrayList<>();
        sideAnswers.add("Ciemna");
        sideAnswers.add("Jasna");
        List<String> timeAnswers = new ArrayList<>();
        timeAnswers.add("Mniej niż 1h 40min");
        timeAnswers.add( "Coś pomiędzy 1h 40min a 2h 30min");
        timeAnswers.add("Nawet cały dzień(więcej niż 2h 30min)");
        List<String> relaseDateAnswers = new ArrayList<>();
        relaseDateAnswers.add("z przed 2002 roku");
        relaseDateAnswers.add( "2002 - 2012");
        relaseDateAnswers.add("2012 - 2022");
        relaseDateAnswers.add("po 2022");

        startingQuestions.add(new QuestionForQuiz(questionAlgorithmRepository.getSideQuestion(),sideAnswers));
        startingQuestions.add(new QuestionForQuiz(questionAlgorithmRepository.getTimeQuestion(),timeAnswers));
        startingQuestions.add(new QuestionForQuiz(questionAlgorithmRepository.getRelaseDateQuestion(),relaseDateAnswers));

        QuestionsResponse questionsResponse = new QuestionsResponse(quizId, startingQuestions);

        return questionsResponse;
    }

    public QuestionsResponse getGenreQuestions(GenreQuestionsRequest genreQuestionsRequest){
        this.saveStartingAnswers(genreQuestionsRequest);

        int amount = 4;
        List<QuestionForQuiz> genreQuestions = new ArrayList<>();
        List<Question> genreQuestionsTemp = questionAlgorithmRepository.getGenreQuestions(amount);
        List<String> answers = new ArrayList<>();

        if(genreQuestionsRequest.getSide().equals("Jasna")){
            answers.addAll(List.of("Romans", "Animacja", "Familyjny", "Komedia", "Muzyczny", "Fantasy", "Sci-Fi",
                    "Western", "Historyczny", "Akcja", "Dokumentalny", "Przygodowy"));

        } else {
            answers.addAll(List.of("Horror", "Thriller", "Dramat", "Wojenny", "Tajemnica", "Fantasy", "Sci-Fi",
                    "Western", "Historyczny", "Akcja", "Dokumentalny", "Przygodowy"));
        }

        Collections.shuffle(answers);

        for(int i = 0; i < genreQuestionsTemp.size(); i++){
            genreQuestions.add(new QuestionForQuiz((genreQuestionsTemp.get(i)), List.of(answers.get(i), answers.get(answers.size()-(i+1)))));
        }

        QuestionsResponse questionsResponse = new QuestionsResponse(genreQuestionsRequest.getQuizId(), genreQuestions);

        return questionsResponse;
    }

    public QuestionsResponse getKeywordQuestions(KeywordQuestionsRequest keywordQuestionsRequest){
        this.saveGenreAnswers(keywordQuestionsRequest);

        int amount = 5;
        List<QuestionForQuiz> keywordQuestions = new ArrayList<>();
        List<Question> keywordQuestionsTemp = questionAlgorithmRepository.getKeywordQuestions(amount);
        List<String> answers = questionAlgorithmRepository.getKeywordsForQuestions(keywordQuestionsRequest.getWantedGenres(),
                keywordQuestionsRequest.getUnwantedGenres(), 2*amount);

        Collections.shuffle(answers);

        for(int i = 0; i < keywordQuestionsTemp.size(); i++){
            keywordQuestions.add(new QuestionForQuiz((keywordQuestionsTemp.get(i)), List.of(answers.get(i), answers.get(answers.size()-(i+1)))));
        }

        QuestionsResponse questionsResponse = new QuestionsResponse(keywordQuestionsRequest.getQuizId(), keywordQuestions);

        return questionsResponse;
    }

    public List<UserMovie> getMovieRecommendations(MovieRecommendationRequest movieRecommendationRequest){
        this.saveKeywordAnswers(movieRecommendationRequest);

        return algorithmServiceImp.getRecommendationV2();
    }

    void saveStartingAnswers(GenreQuestionsRequest genreQuestionsRequest){
        QuizResults sideResult = new QuizResults(genreQuestionsRequest.getQuizId(), genreQuestionsRequest.getUserId(),
                "Side", genreQuestionsRequest.getSide(), "positive");

        QuizResults timeResult = new QuizResults(genreQuestionsRequest.getQuizId(), genreQuestionsRequest.getUserId(),
                "Time", genreQuestionsRequest.getTime(), "positive");

        QuizResults releaseDateResult = new QuizResults(genreQuestionsRequest.getQuizId(), genreQuestionsRequest.getUserId(),
                "ReleaseDate", genreQuestionsRequest.getReleaseDate(), "positive");

        List<QuizResults> listToSave = new ArrayList<>(List.of(sideResult, timeResult, releaseDateResult));

        quizResultsRepository.saveAll(listToSave);
    }

    void saveGenreAnswers(KeywordQuestionsRequest keywordQuestionsRequest){
        List<String> wantedGenresId = questionAlgorithmRepository.getGenresId(keywordQuestionsRequest.getWantedGenres());
        List<String> unwantedGenresId = questionAlgorithmRepository.getGenresId(keywordQuestionsRequest.getUnwantedGenres());
        List<QuizResults> listToSave = new ArrayList<>();

        for(String genreId : wantedGenresId){
            listToSave.add(new QuizResults(keywordQuestionsRequest.getQuizId(), keywordQuestionsRequest.getUserId(),
                    "Genre", genreId, "positive"));
        }

        for(String genreId : unwantedGenresId){
            listToSave.add(new QuizResults(keywordQuestionsRequest.getQuizId(), keywordQuestionsRequest.getUserId(),
                    "Genre", genreId, "negative"));
        }

        quizResultsRepository.saveAll(listToSave);
    }

    void saveKeywordAnswers(MovieRecommendationRequest movieRecommendationRequest){
        List<String> keywords = questionAlgorithmRepository.getKeywordsId(movieRecommendationRequest.getKeywords());
        List<QuizResults> listToSave = new ArrayList<>();

        for(String keywordId : keywords){
            listToSave.add(new QuizResults(movieRecommendationRequest.getQuizId(), movieRecommendationRequest.getUserId(),
                    "Keyword", keywordId, "positive"));
        }

        quizResultsRepository.saveAll(listToSave);
    }
}
