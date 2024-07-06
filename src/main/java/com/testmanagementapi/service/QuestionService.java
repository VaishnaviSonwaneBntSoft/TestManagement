package com.testmanagementapi.service;

import org.springframework.web.multipart.MultipartFile;
import com.testmanagementapi.entity.QuestionModel;
import com.testmanagementapi.exception.CategoryNotFoundException;
import com.testmanagementapi.exception.DuplicateEntries;
import com.testmanagementapi.exception.SubcategoryNotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface QuestionService {

    QuestionModel createMcqQuestion(QuestionModel model);

    List<QuestionModel> getAllQuestionsData();

    QuestionModel updateQuestionData(QuestionModel model, long questionId);

    Optional<QuestionModel> getQuestionDataById(long questionId);

    void deleteQuestionDataById(long questionId);

    void uploadBulkQuestions(MultipartFile multipartFile) throws IOException, CategoryNotFoundException, SubcategoryNotFoundException, DuplicateEntries;
}

