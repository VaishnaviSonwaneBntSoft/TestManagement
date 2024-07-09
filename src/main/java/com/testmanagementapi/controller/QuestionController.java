package com.testmanagementapi.controller;

import java.io.IOException;
import java.util.List;
import org.apache.poi.EncryptedDocumentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.testmanagementapi.entity.QuestionModel;
import com.testmanagementapi.response.SuccessResponse;
import com.testmanagementapi.service.impl.QuestionServiceImpl;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/question")
public class QuestionController {

  private QuestionServiceImpl questionService;

  public QuestionController(QuestionServiceImpl questionService) {
    this.questionService = questionService;
  }

  @PostMapping
  public ResponseEntity<SuccessResponse> createMcqQuestion(@Valid @RequestBody QuestionModel model) {
    log.info("Received request to create a new MCQ question: {}", model);
    questionService.createMcqQuestion(model);
    SuccessResponse successResponse = new SuccessResponse("New Question Saved", 201, model);
    log.info("Successfully created new MCQ question: {}", model);
    return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<SuccessResponse> getAllQuestionsData() {
    List<QuestionModel> dataList = questionService.getAllQuestionsData();
    SuccessResponse successResponse = new SuccessResponse("All Data Retrieved", 200, dataList);
    return new ResponseEntity<>(successResponse, HttpStatus.OK);
  }

  @PutMapping("/{question-id}")
  public ResponseEntity<SuccessResponse> updateQuestionData(@Valid @RequestBody QuestionModel model,
      @PathVariable("question-id") long questionId) {

    QuestionModel model2 = questionService.updateQuestionData(model, questionId);
    SuccessResponse sResponse = new SuccessResponse("Question Data Updated", 200, model2);
    return new ResponseEntity<>(sResponse, HttpStatus.OK);
  }

  @GetMapping("/{question-id}")
  public ResponseEntity<SuccessResponse> getQuestionDataById(@PathVariable("question-id") long questionId) {
    Optional<QuestionModel> model = questionService.getQuestionDataById(questionId);
    SuccessResponse successResponse = new SuccessResponse("Data Retrieved by Id", 200, model);
    return new ResponseEntity<>(successResponse, HttpStatus.OK);
  }

  @DeleteMapping("/{question-id}")
  public ResponseEntity<SuccessResponse> deleteQuestion(@PathVariable("question-id") long questionId) {
    questionService.deleteQuestionDataById(questionId);
    SuccessResponse successResponse = new SuccessResponse("Question Data Deleted", 200, null);
    return new ResponseEntity<>(successResponse, HttpStatus.OK);
  }

  @PostMapping("/upload")
  public ResponseEntity<SuccessResponse> uploadBulkQuestions(@RequestParam("file") MultipartFile file)
      throws EncryptedDocumentException, IOException {
    questionService.uploadBulkQuestions(file);
    SuccessResponse successResponse = new SuccessResponse("Question Data Uploaded", 200, null);
    return new ResponseEntity<>(successResponse, HttpStatus.OK);
  }

}
