package com.testmanagement_api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.testmanagement_api.dao.SubCategoryRepository;
import com.testmanagement_api.dao.TestManagementRepository;
import com.testmanagement_api.entity.Category;
import com.testmanagement_api.entity.Subcategory;
import com.testmanagement_api.entity.TestModel;
import com.testmanagement_api.exceptionhandler.DataNotFoundException;
import com.testmanagement_api.exceptionhandler.DuplicatedDataException;
import com.testmanagement_api.exceptionhandler.SubcategoryNotFoundException;

public class TestManagementServiceTests {

    @Mock
    private TestManagementRepository tRepository;

    @Mock
    private SubCategoryRepository subCategoryRepository;

    @InjectMocks
    private TestManagementService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateMcqQuestion_Success() {
      
        TestModel testModel = new TestModel();
        testModel.setQuestion_id(1L);
        testModel.setQuestion("Sample question");
        
        Subcategory subcategory = new Subcategory();
        subcategory.setSubcategoryId(1L);
        testModel.setSubcategory(subcategory);

        when(tRepository.existsById(1L)).thenReturn(false);
        when(subCategoryRepository.existsById(1L)).thenReturn(true);
        when(tRepository.save(testModel)).thenReturn(testModel);

        TestModel result = service.CreateMcqQuestion(testModel);

        assertNotNull(result);
        assertEquals(testModel.getQuestion_id(), result.getQuestion_id());
        assertEquals(testModel.getQuestion(), result.getQuestion());
    }

    @Test
    public void testCreateMcqQuestion_SubcategoryNotFound() {
   
        TestModel testModel = new TestModel();
        testModel.setQuestion_id(1L);
        testModel.setQuestion("Sample question");
        
        Subcategory subcategory = new Subcategory();
        subcategory.setSubcategoryId(1L);
        testModel.setSubcategory(subcategory);

        when(tRepository.existsById(1L)).thenReturn(false);
        when(subCategoryRepository.existsById(1L)).thenReturn(false);


        assertThrows(SubcategoryNotFoundException.class, () -> {
            service.CreateMcqQuestion(testModel);
        });
    }

    @Test
    public void testCreateMcqQuestion_DuplicateQuestionId() {
        
        TestModel testModel = new TestModel();
        testModel.setQuestion_id(1L);
        testModel.setQuestion("Sample question");
        
        when(tRepository.existsById(1L)).thenReturn(true);

        assertThrows(DuplicatedDataException.class, () -> {
            service.CreateMcqQuestion(testModel);
        });
    }

    @Test
    public void testGetAllQuestionsData() {
     
        List<TestModel> testDataList = new ArrayList<>();
        TestModel testModel1 = new TestModel();
        testModel1.setQuestion_id(1L);
        testModel1.setQuestion("Question 1");

        TestModel testModel2 = new TestModel();
        testModel2.setQuestion_id(2L);
        testModel2.setQuestion("Question 2");

        testDataList.add(testModel1);
        testDataList.add(testModel2);

        when(tRepository.findAll()).thenReturn(testDataList);

        List<TestModel> result = service.GetAllQuestionsData();

        assertEquals(2, result.size());
        assertEquals(testDataList, result);
    }

    @Test
    public void testUpdateQuestionData_Success() {
       
        TestModel testModelToUpdate = new TestModel();
        testModelToUpdate.setQuestion_id(1L);
        testModelToUpdate.setQuestion("Updated question");
        testModelToUpdate.setSubcategory(new Subcategory(101L, new Category(), "Something", "Something"));

        TestModel existingTestModel = new TestModel();
        existingTestModel.setQuestion_id(1L);
        existingTestModel.setQuestion("Original question");
        existingTestModel.setSubcategory(new Subcategory(101L, new Category(), "Something", "Something"));


        when(tRepository.existsById(1L)).thenReturn(true);
        when(tRepository.existsById(existingTestModel.getSubcategory().getSubcategoryId())).thenReturn(true);
        when(tRepository.save(testModelToUpdate)).thenReturn(testModelToUpdate);
       
        TestModel result = service.updateQuestionData(testModelToUpdate, 1L);


        assertNotNull(result);
        assertEquals(testModelToUpdate.getQuestion(), result.getQuestion());
    }

    @Test
    public void testUpdateQuestionData_SubcategoryNotFound() {
     
        TestModel testModelToUpdate = new TestModel();
        testModelToUpdate.setQuestion_id(1L);
        testModelToUpdate.setQuestion("Updated question");
        testModelToUpdate.setSubcategory(new Subcategory(101L, new Category(), "Something", "Something"));

        TestModel existingTestModel = new TestModel();
        existingTestModel.setQuestion_id(1L);
        existingTestModel.setQuestion("Original question");

        when(tRepository.existsById(1L)).thenReturn(true);

        assertThrows(SubcategoryNotFoundException.class, () -> {
            service.updateQuestionData(testModelToUpdate, 1L);
        });
    }

    @Test
    public void testUpdateQuestionData_QuestionNotFound() {
       
        TestModel testModelToUpdate = new TestModel();
        testModelToUpdate.setQuestion_id(1L);
        testModelToUpdate.setQuestion("Updated question");

        when(tRepository.existsById(1L)).thenReturn(false);

        assertThrows(DataNotFoundException.class, () -> {
            service.updateQuestionData(testModelToUpdate, 1L);
        });
    }

    @Test
    public void testGetQuestionDataById_Success() {
       
        TestModel testModel = new TestModel();
        testModel.setQuestion_id(1L);
        testModel.setQuestion("Sample question");

        when(tRepository.existsById(1L)).thenReturn(true);
        when(tRepository.findById(1L)).thenReturn(Optional.of(testModel));

        Optional<TestModel> result = service.getQuestionDataById(1L);

        assertTrue(result.isPresent());
        assertEquals(testModel.getQuestion(), result.get().getQuestion());
    }

    @Test
    public void testGetQuestionDataById_QuestionNotFound() {
       
        when(tRepository.existsById(1L)).thenReturn(false);

        assertThrows(DataNotFoundException.class, () -> {
            service.getQuestionDataById(1L);
        });
    }

    @Test
    public void testDeleteQuestionDataById_Success() {
       
        when(tRepository.existsById(1L)).thenReturn(true);

        service.deleteQuestionDataById(1L);

        verify(tRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteQuestionDataById_QuestionNotFound() {
       
        when(tRepository.existsById(1L)).thenReturn(false);

        assertThrows(DataNotFoundException.class, () -> {
            service.deleteQuestionDataById(1L);
        });
    }
}
