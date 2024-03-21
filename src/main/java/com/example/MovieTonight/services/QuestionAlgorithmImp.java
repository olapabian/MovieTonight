package com.example.MovieTonight.services;

import com.example.MovieTonight.algorithm.*;
import com.example.MovieTonight.enums.Attitude;
import com.example.MovieTonight.enums.ValueType;
import com.example.MovieTonight.model.Question;
import com.example.MovieTonight.model.QuizResults;
import com.example.MovieTonight.repository.QuestionAlgorithmRepository;
import com.example.MovieTonight.repository.QuizResultsRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Getter
@AllArgsConstructor
public class QuestionAlgorithmImp {
    QuestionAlgorithmRepository questionAlgorithmRepository;
    QuizResultsRepository quizResultsRepository;
    AlgorithmServiceImp algorithmServiceImp;

    private static final List<String> lightSide = Arrays.asList("Romans", "Animacja", "Familyjny", "Komedia", "Muzyczny", "Fantasy", "Sci-Fi",
            "Western", "Historyczny", "Akcja", "Dokumentalny", "Przygodowy");
    private static final List<String> darktSide = Arrays.asList("Horror", "Thriller", "Dramat", "Wojenny", "Tajemnica", "Fantasy", "Sci-Fi",
            "Western", "Historyczny", "Akcja", "Dokumentalny", "Przygodowy");

    public StartingQuestionsResponse getStartingQuestions(){
        Question sideQuestion = questionAlgorithmRepository.getSideQuestion();
        Question timeQuestion = questionAlgorithmRepository.getTimeQuestion();
        Question releaseDateQuestion = questionAlgorithmRepository.getRelaseDateQuestion();
        Question adultQuestion = questionAlgorithmRepository.getAdultQuestion();

        StartingQuestionsResponse startingQuestionsResponse =
                new StartingQuestionsResponse(sideQuestion.getContent(), questionAlgorithmRepository.getAnswers(sideQuestion.getId()),
                        timeQuestion.getContent(),questionAlgorithmRepository.getAnswers(timeQuestion.getId()),
                        releaseDateQuestion.getContent(),
                        adultQuestion.getContent(), questionAlgorithmRepository.getAnswers(adultQuestion.getId()));


        return startingQuestionsResponse;
    }

    public GenreQuestionsResponse getGenreQuestions(GenreQuestionsRequest genreQuestionsRequest){
        this.saveStartingAnswers(genreQuestionsRequest);

        int amount = 4;
        List<GenreQuestion> genreQuestions = new ArrayList<>();
        List<Question> genreQuestionsTemp = questionAlgorithmRepository.getGenreQuestions(amount);
        List<String> answers;

        if(genreQuestionsRequest.getSide().equals("Light")){
            answers = lightSide;
        } else {
            answers = darktSide;
        }

        Collections.shuffle(answers);

        int i = 0;
        for(Question question : genreQuestionsTemp){
            genreQuestions.add(new GenreQuestion(question.getContent(), List.of(answers.get(i),answers.get(i+1),answers.get(i+2))));
            i+=3;
        }

        GenreQuestionsResponse genreQuestionsResponse = new GenreQuestionsResponse(genreQuestionsRequest.getQuizId(), genreQuestions);

        return genreQuestionsResponse;
    }

    public KeywordQuestionsResponse getKeywordQuestions(KeywordQuestionsRequest keywordQuestionsRequest){
        this.saveGenreAnswers(keywordQuestionsRequest);

        int amount = 10;
        List<String> question = questionAlgorithmRepository.getKeywordQuestions(1);
        List<String> answers = questionAlgorithmRepository.getKeywordsForQuestions(keywordQuestionsRequest.getWantedGenres(),
                keywordQuestionsRequest.getUnwantedGenres(), amount);

        Collections.shuffle(answers);

        KeywordQuestionsResponse keywordQuestionsResponse = new KeywordQuestionsResponse(keywordQuestionsRequest.getQuizId(),
                question.get(0), answers);

        return keywordQuestionsResponse;
    }

    public List<UserMovie> getMovieRecommendations(MovieRecommendationRequest movieRecommendationRequest){
        this.saveKeywordAnswers(movieRecommendationRequest);

        List<Long> wantedGenres = questionAlgorithmRepository.getWantedGenres(movieRecommendationRequest.getQuizId()).stream()
                .mapToLong(Long::parseLong)
                .boxed()
                .toList();
        List<Long> unwantedGenres = questionAlgorithmRepository.getUnWantedGenres(movieRecommendationRequest.getQuizId()).stream()
                .mapToLong(Long::parseLong)
                .boxed()
                .toList();
        List<Long> wantedKeywords = questionAlgorithmRepository.getWantedKeywords(movieRecommendationRequest.getQuizId()).stream()
                .mapToLong(Long::parseLong)
                .boxed()
                .toList();

        String timeRange = questionAlgorithmRepository.getTimeRange(movieRecommendationRequest.getQuizId());
        String releaseDateRange = questionAlgorithmRepository.getReleaseDateRange(movieRecommendationRequest.getQuizId());
        String[] timeParts = timeRange.split("-");
        String[] releaseDateParts = releaseDateRange.split("-");
        String adult = questionAlgorithmRepository.getAdult(movieRecommendationRequest.getQuizId());

        DataForMovieRecommendations dataForMovieRecommendations = new DataForMovieRecommendations(wantedGenres,
                unwantedGenres, wantedKeywords, Long.parseLong(timeParts[0]), Long.parseLong(timeParts[1]),
                Long.parseLong(releaseDateParts[0]), Long.parseLong(releaseDateParts[0]), adult);

        System.out.println(dataForMovieRecommendations.getMaxTime());

        return algorithmServiceImp.getRecommendationV2();
    }

    void saveStartingAnswers(GenreQuestionsRequest genreQuestionsRequest){
        Long quizId = questionAlgorithmRepository.getQuizId();
        QuizResults sideResult = new QuizResults(quizId, genreQuestionsRequest.getUserId(),
                ValueType.SIDE, questionAlgorithmRepository.getAnswerValue(genreQuestionsRequest.getSide()), Attitude.POSITIVE);

        QuizResults timeResult = new QuizResults(quizId, genreQuestionsRequest.getUserId(),
                ValueType.TIME, questionAlgorithmRepository.getAnswerValue(genreQuestionsRequest.getTime()), Attitude.POSITIVE);

        QuizResults releaseDateResult = new QuizResults(quizId, genreQuestionsRequest.getUserId(),
                ValueType.RELEASE_DATE, genreQuestionsRequest.getReleaseDate(), Attitude.POSITIVE);

        QuizResults adultResult = new QuizResults(quizId, genreQuestionsRequest.getUserId(),
                ValueType.ADULT, getQuestionAlgorithmRepository().getAnswerValue(genreQuestionsRequest.getAdult()), Attitude.POSITIVE);

        List<QuizResults> listToSave = new ArrayList<>(List.of(sideResult, timeResult, releaseDateResult, adultResult));

        quizResultsRepository.saveAll(listToSave);
    }

    void saveGenreAnswers(KeywordQuestionsRequest keywordQuestionsRequest){
        List<String> wantedGenresId = questionAlgorithmRepository.getGenresId(keywordQuestionsRequest.getWantedGenres());
        List<String> unwantedGenresId = questionAlgorithmRepository.getGenresId(keywordQuestionsRequest.getUnwantedGenres());
        List<QuizResults> listToSave = new ArrayList<>();

        for(String genreId : wantedGenresId){
            listToSave.add(new QuizResults(keywordQuestionsRequest.getQuizId(), keywordQuestionsRequest.getUserId(),
                    ValueType.GENRE, genreId, Attitude.POSITIVE));
        }

        for(String genreId : unwantedGenresId){
            listToSave.add(new QuizResults(keywordQuestionsRequest.getQuizId(), keywordQuestionsRequest.getUserId(),
                    ValueType.GENRE, genreId, Attitude.NEGATIVE));
        }

        quizResultsRepository.saveAll(listToSave);
    }

    void saveKeywordAnswers(MovieRecommendationRequest movieRecommendationRequest){
        List<String> keywords = questionAlgorithmRepository.getKeywordsId(movieRecommendationRequest.getKeywords());
        List<QuizResults> listToSave = new ArrayList<>();

        for(String keywordId : keywords){
            listToSave.add(new QuizResults(movieRecommendationRequest.getQuizId(), movieRecommendationRequest.getUserId(),
                    ValueType.KEYWORD, keywordId, Attitude.POSITIVE));
        }

        quizResultsRepository.saveAll(listToSave);
    }
}
