package com.testmanagementapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.testmanagementapi.entity.QuestionModel;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionModel , Long>{
    boolean existsByQuestion(String question);

    @Query("SELECT q FROM QuestionModel q WHERE q.questionId = :questionId")
    QuestionModel getQuestionInstance(Long questionId);
}
