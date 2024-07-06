package com.testmanagementapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.testmanagementapi.entity.QuestionModel;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionModel , Long>{
    boolean existsByQuestion(String question);
}
