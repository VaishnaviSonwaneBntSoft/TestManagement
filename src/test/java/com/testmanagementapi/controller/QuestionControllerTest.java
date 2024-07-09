package com.testmanagementapi.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.EncryptedDocumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.testmanagementapi.entity.QuestionModel;
import com.testmanagementapi.response.SuccessResponse;
import com.testmanagementapi.service.impl.QuestionServiceImpl;

@SpringBootTest
class QuestionControllerTest {

    @Mock
    private QuestionServiceImpl questionService;

    @InjectMocks
    private QuestionController questionController;

    private QuestionModel testQuestion;

    @BeforeEach
    public void setup() {
      
        testQuestion = new QuestionModel();
        testQuestion.setQuestionId(1L);
        testQuestion.setQuestion("Test Question?");
        testQuestion.setCorrectOption("Test Answer");
        
    }

    @Test
    void testCreateMcqQuestion() {
        ResponseEntity<SuccessResponse> response = questionController.createMcqQuestion(testQuestion);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("New Question Saved", response.getBody().getMessage());
        assertEquals(201, response.getBody().getStatusCode());

        verify(questionService, times(1)).createMcqQuestion(testQuestion);
    }

    @Test
    void testGetAllQuestionsData() {
        List<QuestionModel> mockQuestionList = new ArrayList<>();
        mockQuestionList.add(testQuestion);

        when(questionService.getAllQuestionsData()).thenReturn(mockQuestionList);

        ResponseEntity<SuccessResponse> response = questionController.getAllQuestionsData();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("All Data Retrieved", response.getBody().getMessage());
        assertEquals(200, response.getBody().getStatusCode());
        assertEquals(mockQuestionList, response.getBody().getModuleData());

        verify(questionService, times(1)).getAllQuestionsData();
    }

    @Test
    void testUpdateQuestionData() {
        long questionId = 1L;
        testQuestion.setQuestion("Updated Question");

        when(questionService.updateQuestionData(testQuestion, questionId)).thenReturn(testQuestion);

        ResponseEntity<SuccessResponse> response = questionController.updateQuestionData(testQuestion, questionId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Question Data Updated", response.getBody().getMessage());
        assertEquals(200, response.getBody().getStatusCode());
        assertEquals(testQuestion, response.getBody().getModuleData());

        verify(questionService, times(1)).updateQuestionData(testQuestion, questionId);
    }

    @Test
    void testGetQuestionDataById() {
        long questionId = 1L;

        when(questionService.getQuestionDataById(questionId)).thenReturn(Optional.of(testQuestion));

        ResponseEntity<SuccessResponse> response = questionController.getQuestionDataById(questionId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Data Retrieved by Id", response.getBody().getMessage());
        assertEquals(200, response.getBody().getStatusCode());
        assertEquals(Optional.of(testQuestion), response.getBody().getModuleData());

        verify(questionService, times(1)).getQuestionDataById(questionId);
    }

    @Test
    void testDeleteQuestion() {
        long questionId = 1L;

        ResponseEntity<SuccessResponse> response = questionController.deleteQuestion(questionId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Question Data Deleted", response.getBody().getMessage());
        assertEquals(204, response.getBody().getStatusCode());

        verify(questionService, times(1)).deleteQuestionDataById(questionId);
    }

    @Test
    void testUploadBulkQuestions() throws EncryptedDocumentException, IOException {
        MultipartFile mockFile = mock(MultipartFile.class);

        ResponseEntity<SuccessResponse> response = questionController.uploadBulkQuestions(mockFile);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Question Data Uploaded", response.getBody().getMessage());
        assertEquals(200, response.getBody().getStatusCode());

        verify(questionService, times(1)).uploadBulkQuestions(mockFile);
    }
}


