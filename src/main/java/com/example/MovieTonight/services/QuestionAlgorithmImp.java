package com.example.MovieTonight.services;

import com.example.MovieTonight.algorithm.QuestionForQuiz;
import com.example.MovieTonight.model.Question;
import com.example.MovieTonight.repository.QuestionAlgorithmRepository;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Getter
public class QuestionAlgorithmImp {

    QuestionAlgorithmRepository questionAlgorithmRepository;

    public QuestionAlgorithmImp(QuestionAlgorithmRepository questionAlgorithmRepository) {
        this.questionAlgorithmRepository = questionAlgorithmRepository;
    }

    public List<QuestionForQuiz> getStartingQuestions(){
        List<QuestionForQuiz> startingQuestions = new ArrayList<>();

        //chwilowo zhardcodowałem odpowiedzi do startowych pytań
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

        return startingQuestions;
    }

    public List<QuestionForQuiz> getGenreQuestions(int quizId, int userId, String side, String time, String relaseDate){
        //tu zapisywanie odpowiedzi do bazy danych ale chwilowo to pomijam

        List<QuestionForQuiz> genreQuestions = new ArrayList<>();
        List<Question> genreQuestionsTemp = questionAlgorithmRepository.getGenreQuestions(4);
        List<String> answers = new ArrayList<>();

        if(side.equals("Ciemna")){
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


        return genreQuestions;
    }

    public List<QuestionForQuiz> getKeywordQuestions(int quizId, int userId, List<String> wantedGenres, List<String> unwantedGenres){
        //tu zapisywanie odpowiedzi do bazy danych ale chwilowo to pomijam

        List<QuestionForQuiz> keywordQuestions = new ArrayList<>();
        int amount = 5;
        List<Question> keywordQuestionsTemp = questionAlgorithmRepository.getKeywordQuestions(amount);
        List<String> answers = questionAlgorithmRepository.getKeywordsForQuestions(wantedGenres, unwantedGenres, 2*amount);
        Collections.shuffle(answers);

        for(int i = 0; i < keywordQuestionsTemp.size(); i++){
            keywordQuestions.add(new QuestionForQuiz((keywordQuestionsTemp.get(i)), List.of(answers.get(i), answers.get(answers.size()-(i+1)))));
        }

        return keywordQuestions;
    }
}
