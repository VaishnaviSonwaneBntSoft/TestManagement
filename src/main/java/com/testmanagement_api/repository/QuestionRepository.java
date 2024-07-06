package com.testmanagement_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.testmanagement_api.entity.QuestionModel;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionModel , Long>{
    boolean existsByQuestion(String question);
}
