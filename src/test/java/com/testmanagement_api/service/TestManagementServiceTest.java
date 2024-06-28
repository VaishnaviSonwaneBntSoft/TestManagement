package com.testmanagement_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.testmanagement_api.dao.TestManagementRepository;
import com.testmanagement_api.entity.TestModel;
import com.testmanagement_api.exceptionhandler.DataNotFoundException;
import com.testmanagement_api.exceptionhandler.DuplicatedDataException;

@SpringBootTest
public class TestManagementServiceTest {

    @Mock
    private TestManagementRepository mockRepository;

    @InjectMocks
    private TestManagementService testService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateMcqQuestion_Success() {
     
        TestModel testModel = new TestModel(1L, "spring", "Question", "Option A", "Option B", "Option C", "Option D", "Option A", "3", "-1");

        when(mockRepository.existsById(1L)).thenReturn(false);

        when(mockRepository.save(testModel)).thenReturn(testModel);

        TestModel result = testService.CreateMcqQuestion(testModel);

        assertEquals(testModel, result);
    }

    @Test
    public void testCreateMcqQuestion_DuplicateId() {
       
        TestModel testModel = new TestModel(1L, "spring", "Question", "Option A", "Option B", "Option C", "Option D", "Option A", "3", "-1");

        when(mockRepository.existsById(1L)).thenReturn(true);

        DuplicatedDataException exception = assertThrows(DuplicatedDataException.class, () -> {
            testService.CreateMcqQuestion(testModel);
        });

        assertEquals("Data Already Present By Given Id", exception.getMessage());
    }

    @Test
    public void testGetAllQuestionsData() {
    
        TestModel testModel = new TestModel(1L, "spring", "Question", "Option A", "Option B", "Option C", "Option D", "Option A", "3", "-1");
        TestModel testModel2 = new TestModel(2L, "springCore", "Question2", "Option A", "Option B", "Option C", "Option D", "Option A", "3", "-1");

        List<TestModel> testData = new ArrayList<TestModel>();
        testData.add(testModel2);
        testData.add(testModel);

        when(mockRepository.findAll()).thenReturn(testData);

        List<TestModel> result = testService.GetAllQuestionsData();

        assertEquals(testData.size(), result.size());
        assertEquals(testData, result);
    }

    @Test
    public void testUpdateQuestionData_Success() {
       
        TestModel testModel = new TestModel(1L, "spring", "Question", "Option A", "Option B", "Option C", "Option D", "Option A", "3", "-1");
        TestModel updatedModel = new TestModel(2L, "springCore", "Question2", "Option A", "Option B", "Option C", "Option D", "Option A", "3", "-1");


        when(mockRepository.existsById(testModel.getQuestion_id())).thenReturn(true);
        when(mockRepository.save(any(TestModel.class))).thenReturn(updatedModel);

        TestModel result = testService.updateQuestionData(updatedModel, 1L);

        assertEquals(updatedModel, result);
    }

    @Test
    public void testUpdateQuestionData_NotFound() {
    
        TestModel updatedModel = new TestModel(2L, "springCore", "Question2", "Option A", "Option B", "Option C", "Option D", "Option A", "3", "-1");
        
        when(mockRepository.existsById(1L)).thenReturn(false);

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
            testService.updateQuestionData(updatedModel, 1L);
        });

        assertEquals("Data Not Found For Provided ID", exception.getMessage());
    }

    @Test
    public void testGetQuestionDataById_Success() {
    
        TestModel testModel = new TestModel(1L, "spring", "Question", "Option A", "Option B", "Option C", "Option D", "Option A", "3", "-1");
       
        when(mockRepository.existsById(testModel.getQuestion_id())).thenReturn(true);
        when(mockRepository.findById(testModel.getQuestion_id())).thenReturn(Optional.of(testModel));

        Optional<TestModel> result = testService.getQuestionDataById(1L);

        assertEquals(testModel, result.orElse(null));
    }

    @Test
    public void testGetQuestionDataById_NotFound() {
    
        when(mockRepository.existsById(1L)).thenReturn(false);

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
            testService.getQuestionDataById(1L);
        });

        assertEquals("Data Not Found For Provided ID", exception.getMessage());
    }

    @Test
    public void testDeleteQuestionDataById_Success() {
 
        TestModel testModel = new TestModel(1L, "spring", "Question", "Option A", "Option B", "Option C", "Option D", "Option A", "3", "-1");

        when(mockRepository.existsById(testModel.getQuestion_id())).thenReturn(true);

        testService.deleteQuestionDataById(testModel.getQuestion_id());

        verify(mockRepository, times(1)).deleteById(testModel.getQuestion_id());
    }

    @Test
    public void testDeleteQuestionDataById_NotFound() {
       
        when(mockRepository.existsById(1L)).thenReturn(false);

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
            testService.deleteQuestionDataById(1L);
        });

        assertEquals("Data Not Found For Provided ID", exception.getMessage());
    }
}
