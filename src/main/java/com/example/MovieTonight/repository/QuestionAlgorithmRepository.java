package com.example.MovieTonight.repository;

import com.example.MovieTonight.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionAlgorithmRepository extends JpaRepository<Question,Long> {
    @Query("SELECT COALESCE(MAX(quizId), 0) + 1 FROM QuizResults")
    Long getQuizId();

    @Query("select q from Question q where q.type = 'Side' order by random() limit 1")
    Question getSideQuestion();

    @Query("select q from Question q where q.type = 'Time' order by random() limit 1")
    Question getTimeQuestion();

    @Query("select q from Question q where q.type = 'ReleaseDate' order by random() limit 1")
    Question getRelaseDateQuestion();
    @Query("select q from Question q where q.type = 'Adult' order by random() limit 1")
    Question getAdultQuestion();

    @Query("select a.content from Answers a where a.questionId = :id")
    List<String> getAnswers(@Param("id") Long id);

    @Query("select q from Question q where q.type = 'Genre' order by random() limit :amount" )
    List<Question> getGenreQuestions(@Param("amount") int amount);

    @Query("select q.content from Question q where q.type = 'Keyword' order by random() limit :amount" )
    List<String> getKeywordQuestions(@Param("amount") int amount);

    @Query("select a.value from Answers a where a.content = :content" )
    String getAnswerValue(@Param("content") String content);

    @Query("select q.id from GenresInfo q where q.name in (:genreNames)")
    List<String> getGenresId(@Param("genreNames") List<String> genreNames);

    @Query("select k.id from KeywordsInfo k where k.name in (:keywordNames)")
    List<String> getKeywordsId(@Param("keywordNames") List<String> keywordNames);

    @Query("select qr.value from QuizResults qr where qr.quizId = :quizId and qr.valueType='GENRE' and qr.attitude='POSITIVE'")
    List<String> getWantedGenres(@Param("quizId") Long quizId);

    @Query("select qr.value from QuizResults qr where qr.quizId = :quizId and qr.valueType='GENRE' and qr.attitude='NEGATIVE'")
    List<String> getUnWantedGenres(@Param("quizId") Long quizId);

    @Query("select qr.value from QuizResults qr where qr.quizId = :quizId and qr.valueType='KEYWORD' and qr.attitude='POSITIVE'")
    List<String> getWantedKeywords(@Param("quizId") Long quizId);

    @Query("select qr.value from QuizResults qr where qr.quizId = :quizId and qr.valueType='TIME' and qr.attitude='POSITIVE'")
    String getTimeRange(@Param("quizId") Long quizId);

    @Query("select qr.value from QuizResults qr where qr.quizId = :quizId and qr.valueType='RELEASE_DATE' and qr.attitude='POSITIVE'")
    String getReleaseDateRange(@Param("quizId") Long quizId);
    @Query("select qr.value from QuizResults qr where qr.quizId = :quizId and qr.valueType='ADULT' and qr.attitude='POSITIVE'")
    String getAdult(@Param("quizId") Long quizId);

    @Query(nativeQuery = true, value = "select ki.name from movie_keywords mk join keywords_info ki on mk.keyword_id=ki.keyword_id" +
            " join (SELECT m.tmdb_id, m.title, m.original_title, count (distinct gi.name)" +
            " FROM movie m INNER JOIN movie_genres mg on mg. tmdb_id = m. tmdb_id inner join" +
            " genres_info gi on gi.genre_id = mg. genre_id where gi.name In (:wantedGenres)" +
            " and gi.name not in (:unwantedGenres)" +
            " group by m. tmdb_id, m.title, m.original_title having count (distinct gi.name) > 1) as dramaty_historyczne" +
            " on mk. tmdb_id = dramaty_historyczne.tmdb_id group by ki.name order by count(*) desc limit :amount")
    List<String> getKeywordsForQuestions(@Param("wantedGenres") List<String> wantedGenres, @Param("unwantedGenres") List<String> unwantedGenres, @Param("amount") int amount);
}
