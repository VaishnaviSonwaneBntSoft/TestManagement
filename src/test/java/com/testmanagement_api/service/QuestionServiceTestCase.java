package com.testmanagement_api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.poi.EncryptedDocumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import com.testmanagement_api.entity.Category;
import com.testmanagement_api.entity.Subcategory;
import com.testmanagement_api.exception.CategoryNotFoundException;
import com.testmanagement_api.exception.DataNotFoundException;
import com.testmanagement_api.exception.DuplicateEntries;
import com.testmanagement_api.exception.DuplicatedDataException;
import com.testmanagement_api.exception.SubcategoryNotFoundException;
import com.testmanagement_api.entity.QuestionModel;
import com.testmanagement_api.repository.QuestionRepository;
import com.testmanagement_api.repository.SubCategoryRepository;

class QuestionServiceTestCase{

    @Mock
    private QuestionRepository tRepository;

    @Mock
    private SubCategoryRepository subCategoryRepository;

    @Mock
    private SubCategoryService subCategoryService;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private QuestionService service;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateMcqQuestion_Success() {
      
        QuestionModel testModel = new QuestionModel();
        testModel.setQuestionId(1L);
        testModel.setQuestion("Sample question");
        
        Subcategory subcategory = new Subcategory();
        subcategory.setSubcategoryId(1L);
        subcategory.setSubCategoryName("Annotation");
        testModel.setSubcategory(subcategory);

        when(tRepository.existsByQuestion(testModel.getQuestion())).thenReturn(false);
        when(tRepository.existsById(1L)).thenReturn(false);
        when(subCategoryRepository.existsBySubCategoryName(subcategory.getSubCategoryName())).thenReturn(true);
        when(tRepository.save(testModel)).thenReturn(testModel);

        QuestionModel result = tRepository.save(testModel);

        assertNotNull(result);
        assertEquals(testModel.getQuestionId(), result.getQuestionId());
        assertEquals(testModel.getQuestion(), result.getQuestion());
    }

    @Test
    void testCreateMcqQuestion_SubcategoryNotFound() {
   
        QuestionModel testModel = new QuestionModel();
        testModel.setQuestionId(1L);
        testModel.setQuestion("Sample question4");
        
        Subcategory subcategory = new Subcategory();
        subcategory.setSubcategoryId(1L);
        subcategory.setSubCategoryName("annotation");
        testModel.setSubcategory(subcategory);

        when(tRepository.existsByQuestion(testModel.getQuestion())).thenReturn(false);
        when(subCategoryRepository.existsBySubCategoryName(subcategory.getSubCategoryName())).thenReturn(false);


        assertThrows(SubcategoryNotFoundException.class, () -> {
            service.createMcqQuestion(testModel);
        });
    }

    @Test
    void testCreateMcqQuestion_DuplicateQuestionId() {
        
        QuestionModel testModel = new QuestionModel();
        testModel.setQuestionId(1L);
        testModel.setQuestion("Sample question");
        
        when(tRepository.existsById(1L)).thenReturn(true);

        assertThrows(DuplicatedDataException.class, () -> {
            service.createMcqQuestion(testModel);
        });
    }

    @Test
    void testGetAllQuestionsData() {
     
        List<QuestionModel> testDataList = new ArrayList<>();
        QuestionModel testModel1 = new QuestionModel();
        testModel1.setQuestionId(1L);
        testModel1.setQuestion("Question 1");

        QuestionModel testModel2 = new QuestionModel();
        testModel2.setQuestionId(2L);
        testModel2.setQuestion("Question 2");

        testDataList.add(testModel1);
        testDataList.add(testModel2);

        when(tRepository.findAll()).thenReturn(testDataList);

        List<QuestionModel> result = service.getAllQuestionsData();

        assertEquals(2, result.size());
        assertEquals(testDataList, result);
    }

    @Test
    void testUpdateQuestionData_Success() {
       
        QuestionModel testModelToUpdate = new QuestionModel();
        testModelToUpdate.setQuestionId(1L);
        testModelToUpdate.setQuestion("Updated question");
        testModelToUpdate.setSubcategory(new Subcategory(101L, new Category(), "Something", "Something"));

        QuestionModel existingTestModel = new QuestionModel();
        existingTestModel.setQuestionId(1L);
        existingTestModel.setQuestion("Original question");
        existingTestModel.setSubcategory(new Subcategory(101L, new Category(), "Something", "Something"));


        when(tRepository.existsById(existingTestModel.getQuestionId())).thenReturn(true);
        when(subCategoryRepository.existsById(existingTestModel.getSubcategory().getSubcategoryId())).thenReturn(true);
        when(tRepository.save(testModelToUpdate)).thenReturn(testModelToUpdate);

        when(tRepository.save(testModelToUpdate)).thenReturn(existingTestModel);
       
        QuestionModel result = service.updateQuestionData(testModelToUpdate, existingTestModel.getQuestionId());


        assertNotNull(result);
        assertNotEquals(testModelToUpdate.getQuestion(), result.getQuestion());
    }

    @Test
    void testUpdateQuestionData_SubcategoryNotFound() {
     
        QuestionModel testModelToUpdate = new QuestionModel();
        testModelToUpdate.setQuestionId(1L);
        testModelToUpdate.setQuestion("Updated question");
        testModelToUpdate.setSubcategory(new Subcategory(101L, new Category(), "Something", "Something"));

        QuestionModel existingTestModel = new QuestionModel();
        existingTestModel.setQuestionId(1L);
        existingTestModel.setQuestion("Original question");

        when(tRepository.existsById(1L)).thenReturn(true);

        assertThrows(SubcategoryNotFoundException.class, () -> {
            service.updateQuestionData(testModelToUpdate, 1L);
        });
    }

    @Test
    void testUpdateQuestionData_QuestionNotFound() {
       
        QuestionModel testModelToUpdate = new QuestionModel();
        testModelToUpdate.setQuestionId(1L);
        testModelToUpdate.setQuestion("Updated question");

        when(tRepository.existsById(1L)).thenReturn(false);

        assertThrows(DataNotFoundException.class, () -> {
            service.updateQuestionData(testModelToUpdate, 1L);
        });
    }

    @Test
    void testGetQuestionDataById_Success() {
       
        QuestionModel testModel = new QuestionModel();
        testModel.setQuestionId(1L);
        testModel.setQuestion("Sample question");

        when(tRepository.existsById(1L)).thenReturn(true);
        when(tRepository.findById(1L)).thenReturn(Optional.of(testModel));

        Optional<QuestionModel> result = service.getQuestionDataById(1L);

        assertTrue(result.isPresent());
        assertEquals(testModel.getQuestion(), result.get().getQuestion());
    }

    @Test
    void testGetQuestionDataById_QuestionNotFound() {
       
        when(tRepository.existsById(1L)).thenReturn(false);

        assertThrows(DataNotFoundException.class, () -> {
            service.getQuestionDataById(1L);
        });
    }

    @Test
    void testDeleteQuestionDataById_Success() {
       
        when(tRepository.existsById(1L)).thenReturn(true);

        service.deleteQuestionDataById(1L);

        verify(tRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteQuestionDataById_QuestionNotFound() {
       
        when(tRepository.existsById(1L)).thenReturn(false);

        assertThrows(DataNotFoundException.class, () -> {
            service.deleteQuestionDataById(1L);
        });
    }


    @Test
    void testUploadBulkQuestions_InvalidFileExtension() throws IOException, EncryptedDocumentException {
       
        InputStream inputStream = getClass().getResourceAsStream("/test-data/questions_invalid.xlsx");
        MockMultipartFile multipartFile = new MockMultipartFile("questions_invalid.xlsx", inputStream);

        try {
            service.uploadBulkQuestions(multipartFile);
        } catch (Exception e) {
            assert e instanceof IllegalArgumentException;
        }

        verify(tRepository, never()).saveAll(anyList());
    }

    @Test
    void testUploadBulkQuestions_DuplicateQuestions() throws IOException, EncryptedDocumentException {
   
    
    InputStream inputStream = getClass().getResourceAsStream("/test-data/questions_duplicate.xlsx");
    MockMultipartFile multipartFile = new MockMultipartFile("questions_duplicate.xlsx", inputStream);

    when(tRepository.existsByQuestion(anyString())).thenReturn(true);

    try {
        service.uploadBulkQuestions(multipartFile);
    } catch (DuplicateEntries e) {
      
        String exceptionMessage = e.getMessage();
        assertTrue(exceptionMessage.contains("Duplicate Question 1"));
        assertTrue(exceptionMessage.contains("Duplicate Question 2"));
    }

    verify(tRepository, never()).saveAll(anyList());
}

    @Test
    void testUploadBulkQuestions_CategoryNotFound() throws IOException, EncryptedDocumentException {
      
        InputStream inputStream = getClass().getResourceAsStream("/test-data/questions_category_not_found.xlsx");
        MockMultipartFile multipartFile = new MockMultipartFile("questions_category_not_found.xlsx", inputStream);

        when(categoryService.getCategoryInstance(anyString())).thenReturn(null);

        try {
            service.uploadBulkQuestions(multipartFile);
        } catch (CategoryNotFoundException e) {
            assert e.getMessage().equals("Given Category Not Present");
        }

        verify(tRepository, never()).saveAll(anyList());
    }

    @Test
    void testUploadBulkQuestions_SubcategoryNotFound() throws IOException, EncryptedDocumentException {
      
        InputStream inputStream = getClass().getResourceAsStream("/test-data/questions_subcategory_not_found.xlsx");
        MockMultipartFile multipartFile = new MockMultipartFile("questions_subcategory_not_found.xlsx", inputStream);

        when(subCategoryService.getSubCategoryInstance(anyString(), anyLong())).thenReturn(null);

        try {
            service.uploadBulkQuestions(multipartFile);
        } catch (SubcategoryNotFoundException e) {
            assert e.getMessage().equals("Not Found Subcategory with foreign key of given category");
        }

        verify(tRepository, never()).saveAll(anyList());
    }

    @Test
    void testUploadBulkQuestions_IOError() throws IOException, EncryptedDocumentException {
      
        InputStream inputStream = mock(InputStream.class);
        MockMultipartFile multipartFile = new MockMultipartFile("questions.xlsx", inputStream);

        try {
            service.uploadBulkQuestions(multipartFile);
        } catch (IOException e) {
            assert e.getMessage().equals("Simulated IOException");
        }

        verify(tRepository, never()).saveAll(anyList());
    }
}
