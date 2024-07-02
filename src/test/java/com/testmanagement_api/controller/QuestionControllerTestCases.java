package com.testmanagement_api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.testmanagement_api.entity.QuestionModel;
import com.testmanagement_api.exceptionhandler.DuplicateEntries;
import com.testmanagement_api.reponsehandler.SuccessResponse;
import com.testmanagement_api.service.QuestionService;

public class QuestionControllerTestCases {

    @Mock
    private QuestionService tService;

    @InjectMocks
    private QuestionController controller;

     @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testCreateMcqQuestion() {
       
        QuestionModel testModel = new QuestionModel();
        testModel.setQuestion_id(1L);
        testModel.setQuestion("Sample question");

        ResponseEntity<SuccessResponse> responseEntity = controller.CreateMcqQuestion(testModel);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        assertEquals("New Question Saved", responseEntity.getBody().getMessage());

        assertEquals(testModel, responseEntity.getBody().getModuleData());
    }

    @Test
    public void testGetAllQuestionsData() {
       
        List<QuestionModel> testDataList = new ArrayList<>();
        QuestionModel testModel = new QuestionModel();
        testModel.setQuestion_id(1L);
        testModel.setQuestion("Sample question");

        QuestionModel testModel2 = new QuestionModel();
        testModel.setQuestion_id(1L);
        testModel.setQuestion("Sample question");

        testDataList.add(testModel2);
        testDataList.add(testModel);

       
        when(tService.GetAllQuestionsData()).thenReturn(testDataList);

      
        ResponseEntity<SuccessResponse> responseEntity = controller.GetAllQuestionsData();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("All Data Retrived", responseEntity.getBody().getMessage());
        assertEquals(testDataList, responseEntity.getBody().getModuleData());
    }

    @Test
    public void testUpdateQuestionData() {
       
        QuestionModel testModel = new QuestionModel();
        testModel.setQuestion_id(1L);
        testModel.setQuestion("Updated question");

        QuestionModel testModel2 = new QuestionModel();
        testModel.setQuestion_id(1L);
        testModel.setQuestion("Sample question");

        when(tService.updateQuestionData(testModel2 , testModel.getQuestion_id())).thenReturn(testModel);

        ResponseEntity<SuccessResponse> responseEntity = controller.updateQuestionData(testModel, 1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Question Data Updated", responseEntity.getBody().getMessage());
        
    }

    @Test
    public void testGetQuestionDataById() {

        QuestionModel testModel = new QuestionModel();
        testModel.setQuestion_id(1L);
        testModel.setQuestion("Updated question");

        when(tService.getQuestionDataById(testModel.getQuestion_id())).thenReturn(Optional.of(testModel));

        ResponseEntity<SuccessResponse> responseEntity = controller.getQuestionDataById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Data Retrived by Id", responseEntity.getBody().getMessage());
    }

    @Test
    public void testDeleteQuestionDataById() {
        
        QuestionModel testModel = new QuestionModel();
        testModel.setQuestion_id(1L);
        testModel.setQuestion("Updated question");

        ResponseEntity<SuccessResponse> responseEntity = controller.deleteQuestion(testModel.getQuestion_id());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Question Data Deleted", responseEntity.getBody().getMessage());
    }


    @Test
    public void testUploadBulkQuestions_Success() throws IOException {
     
        MultipartFile mockFile = mock(MultipartFile.class);
        SuccessResponse expectedResponse = new SuccessResponse("Question Data Uploaded", 200, null);
   
        doNothing().when(tService).uploadBulkQuestions(mockFile);
        
        ResponseEntity<SuccessResponse> response = controller.uploadBulkQuestions(mockFile);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse.getMessage(), response.getBody().getMessage());
        assertEquals(expectedResponse.getStatusCode(), response.getBody().getStatusCode());
        assertNull(response.getBody().getModuleData());

        verify(tService, times(1)).uploadBulkQuestions(mockFile);
    }

    @Test
    public void testUploadBulkQuestions_DuplicateEntries() throws IOException {
        
        MultipartFile mockFile = mock(MultipartFile.class);
        List<String> duplicateList = Arrays.asList("Question 1", "Question 2"); 
        DuplicateEntries exception = new DuplicateEntries(duplicateList);
        SuccessResponse expectedResponse = new SuccessResponse("Duplicate Questions Found In Excel Sheet", 400, duplicateList);

        doThrow(exception).when(tService).uploadBulkQuestions(mockFile);

        ResponseEntity<SuccessResponse> response = controller.uploadBulkQuestions(mockFile);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(expectedResponse.getMessage(), response.getBody().getMessage());
        assertEquals(expectedResponse.getStatusCode(), response.getBody().getStatusCode());
        assertEquals(duplicateList, response.getBody().getModuleData());

        verify(tService, times(1)).uploadBulkQuestions(mockFile);
    }

    @Test
    public void testUploadBulkQuestions_Exception() throws IOException {
        
        MultipartFile mockFile = mock(MultipartFile.class);
        RuntimeException exception = new RuntimeException("Some error message");
        SuccessResponse expectedResponse = new SuccessResponse(exception.getMessage(), 400, null);

        doThrow(exception).when(tService).uploadBulkQuestions(mockFile);

        ResponseEntity<SuccessResponse> response = controller.uploadBulkQuestions(mockFile);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(expectedResponse.getMessage(), response.getBody().getMessage());
        assertEquals(expectedResponse.getStatusCode(), response.getBody().getStatusCode());
        assertNull(response.getBody().getModuleData());

        verify(tService, times(1)).uploadBulkQuestions(mockFile);
    }
}


