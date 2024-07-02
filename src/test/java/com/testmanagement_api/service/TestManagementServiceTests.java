package com.testmanagement_api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import com.testmanagement_api.dao.SubCategoryRepository;
import com.testmanagement_api.dao.QuestionRepository;
import com.testmanagement_api.entity.Category;
import com.testmanagement_api.entity.Subcategory;
import com.testmanagement_api.entity.QuestionModel;
import com.testmanagement_api.exceptionhandler.CategoryNotFoundException;
import com.testmanagement_api.exceptionhandler.DataNotFoundException;
import com.testmanagement_api.exceptionhandler.DuplicateEntries;
import com.testmanagement_api.exceptionhandler.DuplicatedDataException;
import com.testmanagement_api.exceptionhandler.SubcategoryNotFoundException;

public class TestManagementServiceTests {

    @Mock
    private QuestionRepository tRepository;

    @Mock
    private SubCategoryRepository subCategoryRepository;

    @InjectMocks
    private QuestionService service;

    @Mock
    private SubCategoryService subCategoryService;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private QuestionService questionService;

    private Workbook mockWorkbook;
    private List<QuestionModel> mockQuestions;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        mockWorkbook = mock(Workbook.class);
        mockQuestions = new ArrayList<>();

        try {
            when(WorkbookFactory.create(any(InputStream.class))).thenReturn(mockWorkbook);
        } catch (IOException | EncryptedDocumentException e) {
            e.printStackTrace();
        }

        doAnswer(invocation -> {
            List<QuestionModel> savedQuestions = invocation.getArgument(0);
            mockQuestions.addAll(savedQuestions);
            return savedQuestions;
        }).when(tRepository).saveAll(anyList());


        when(categoryService.getCategoryInstance(anyString())).thenReturn(new Category());

        when(subCategoryService.getSubCategoryInstance(anyString(), anyLong())).thenReturn(new Subcategory());

    }

    @Test
    public void testCreateMcqQuestion_Success() {
      
        QuestionModel testModel = new QuestionModel();
        testModel.setQuestion_id(1L);
        testModel.setQuestion("Sample question");
        
        Subcategory subcategory = new Subcategory();
        subcategory.setSubcategoryId(1L);
        testModel.setSubcategory(subcategory);

        when(tRepository.existsById(1L)).thenReturn(false);
        when(subCategoryRepository.existsById(1L)).thenReturn(true);
        when(tRepository.save(testModel)).thenReturn(testModel);

        QuestionModel result = service.CreateMcqQuestion(testModel);

        assertNotNull(result);
        assertEquals(testModel.getQuestion_id(), result.getQuestion_id());
        assertEquals(testModel.getQuestion(), result.getQuestion());
    }

    @Test
    public void testCreateMcqQuestion_SubcategoryNotFound() {
   
        QuestionModel testModel = new QuestionModel();
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
        
        QuestionModel testModel = new QuestionModel();
        testModel.setQuestion_id(1L);
        testModel.setQuestion("Sample question");
        
        when(tRepository.existsById(1L)).thenReturn(true);

        assertThrows(DuplicatedDataException.class, () -> {
            service.CreateMcqQuestion(testModel);
        });
    }

    @Test
    public void testGetAllQuestionsData() {
     
        List<QuestionModel> testDataList = new ArrayList<>();
        QuestionModel testModel1 = new QuestionModel();
        testModel1.setQuestion_id(1L);
        testModel1.setQuestion("Question 1");

        QuestionModel testModel2 = new QuestionModel();
        testModel2.setQuestion_id(2L);
        testModel2.setQuestion("Question 2");

        testDataList.add(testModel1);
        testDataList.add(testModel2);

        when(tRepository.findAll()).thenReturn(testDataList);

        List<QuestionModel> result = service.GetAllQuestionsData();

        assertEquals(2, result.size());
        assertEquals(testDataList, result);
    }

    @Test
    public void testUpdateQuestionData_Success() {
       
        QuestionModel testModelToUpdate = new QuestionModel();
        testModelToUpdate.setQuestion_id(1L);
        testModelToUpdate.setQuestion("Updated question");
        testModelToUpdate.setSubcategory(new Subcategory(101L, new Category(), "Something", "Something"));

        QuestionModel existingTestModel = new QuestionModel();
        existingTestModel.setQuestion_id(1L);
        existingTestModel.setQuestion("Original question");
        existingTestModel.setSubcategory(new Subcategory(101L, new Category(), "Something", "Something"));


        when(tRepository.existsById(existingTestModel.getQuestion_id())).thenReturn(true);
        when(subCategoryRepository.existsById(existingTestModel.getSubcategory().getSubcategoryId())).thenReturn(true);
        when(tRepository.save(testModelToUpdate)).thenReturn(testModelToUpdate);

        when(tRepository.save(testModelToUpdate)).thenReturn(existingTestModel);
       
        QuestionModel result = service.updateQuestionData(testModelToUpdate, existingTestModel.getQuestion_id());


        assertNotNull(result);
        assertNotEquals(testModelToUpdate.getQuestion(), result.getQuestion());
    }

    @Test
    public void testUpdateQuestionData_SubcategoryNotFound() {
     
        QuestionModel testModelToUpdate = new QuestionModel();
        testModelToUpdate.setQuestion_id(1L);
        testModelToUpdate.setQuestion("Updated question");
        testModelToUpdate.setSubcategory(new Subcategory(101L, new Category(), "Something", "Something"));

        QuestionModel existingTestModel = new QuestionModel();
        existingTestModel.setQuestion_id(1L);
        existingTestModel.setQuestion("Original question");

        when(tRepository.existsById(1L)).thenReturn(true);

        assertThrows(SubcategoryNotFoundException.class, () -> {
            service.updateQuestionData(testModelToUpdate, 1L);
        });
    }

    @Test
    public void testUpdateQuestionData_QuestionNotFound() {
       
        QuestionModel testModelToUpdate = new QuestionModel();
        testModelToUpdate.setQuestion_id(1L);
        testModelToUpdate.setQuestion("Updated question");

        when(tRepository.existsById(1L)).thenReturn(false);

        assertThrows(DataNotFoundException.class, () -> {
            service.updateQuestionData(testModelToUpdate, 1L);
        });
    }

    @Test
    public void testGetQuestionDataById_Success() {
       
        QuestionModel testModel = new QuestionModel();
        testModel.setQuestion_id(1L);
        testModel.setQuestion("Sample question");

        when(tRepository.existsById(1L)).thenReturn(true);
        when(tRepository.findById(1L)).thenReturn(Optional.of(testModel));

        Optional<QuestionModel> result = service.getQuestionDataById(1L);

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

    @Test
    public void testUploadBulkQuestions_ValidFile() throws IOException, EncryptedDocumentException {
     
        String filePath = "c:/Users/vaishnavi.sonawane/Downloads/QuestionBank.xlsx";
        File file = new File(filePath);

        if (!file.exists()) {
            throw new RuntimeException("File not found: " + filePath);
        }

         FileInputStream inputStream = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile(file.getName(), inputStream);

        Sheet mockSheet = mock(Sheet.class);
        when(mockWorkbook.getSheetAt(0)).thenReturn(mockSheet);

        List<Row> mockRows = createMockRows();
        when(mockSheet.iterator()).thenReturn(mockRows.iterator());

        questionService.uploadBulkQuestions(multipartFile);

        verify(tRepository, times(1)).saveAll(anyList());
    }

    @Test
    public void testUploadBulkQuestions_InvalidFileExtension() throws IOException, EncryptedDocumentException {
       
        InputStream inputStream = getClass().getResourceAsStream("/test-data/questions_invalid.xlsx");
        MockMultipartFile multipartFile = new MockMultipartFile("questions_invalid.xlsx", inputStream);

        try {
            questionService.uploadBulkQuestions(multipartFile);
        } catch (Exception e) {
            assert e instanceof IllegalArgumentException;
        }

        verify(tRepository, never()).saveAll(anyList());
    }

    @Test
public void testUploadBulkQuestions_DuplicateQuestions() throws IOException, EncryptedDocumentException {
   
    
    InputStream inputStream = getClass().getResourceAsStream("/test-data/questions_duplicate.xlsx");
    MockMultipartFile multipartFile = new MockMultipartFile("questions_duplicate.xlsx", inputStream);

    when(tRepository.existsByQuestion(anyString())).thenReturn(true);

    try {
        questionService.uploadBulkQuestions(multipartFile);
        fail("Expected DuplicateEntries exception was not thrown");
    } catch (DuplicateEntries e) {
      
        String exceptionMessage = e.getMessage();
        assertTrue(exceptionMessage.contains("Duplicate Question 1"));
        assertTrue(exceptionMessage.contains("Duplicate Question 2"));
    }

    verify(tRepository, never()).saveAll(anyList());
}

    @Test
    public void testUploadBulkQuestions_CategoryNotFound() throws IOException, EncryptedDocumentException {
      
        InputStream inputStream = getClass().getResourceAsStream("/test-data/questions_category_not_found.xlsx");
        MockMultipartFile multipartFile = new MockMultipartFile("questions_category_not_found.xlsx", inputStream);

        when(categoryService.getCategoryInstance(anyString())).thenReturn(null);

        try {
            questionService.uploadBulkQuestions(multipartFile);
        } catch (CategoryNotFoundException e) {
            assert e.getMessage().equals("Given Category Not Present");
        }

        verify(tRepository, never()).saveAll(anyList());
    }

    @Test
    public void testUploadBulkQuestions_SubcategoryNotFound() throws IOException, EncryptedDocumentException {
      
        InputStream inputStream = getClass().getResourceAsStream("/test-data/questions_subcategory_not_found.xlsx");
        MockMultipartFile multipartFile = new MockMultipartFile("questions_subcategory_not_found.xlsx", inputStream);

        when(subCategoryService.getSubCategoryInstance(anyString(), anyLong())).thenReturn(null);

        try {
            questionService.uploadBulkQuestions(multipartFile);
        } catch (SubcategoryNotFoundException e) {
            assert e.getMessage().equals("Not Found Subcategory with foriegn key of given category");
        }

        verify(tRepository, never()).saveAll(anyList());
    }

    @Test
    public void testUploadBulkQuestions_IOError() throws IOException, EncryptedDocumentException {
      
        InputStream inputStream = mock(InputStream.class);
        MockMultipartFile multipartFile = new MockMultipartFile("questions.xlsx", inputStream);

        when(WorkbookFactory.create(any(InputStream.class))).thenThrow(new IOException("Simulated IOException"));

        try {
            questionService.uploadBulkQuestions(multipartFile);
        } catch (IOException e) {
            assert e.getMessage().equals("Simulated IOException");
        }

        verify(tRepository, never()).saveAll(anyList());
    }

    private List<Row> createMockRows() {
      
        List<Row> rows = new ArrayList<>();
        Sheet mockSheet = mock(Sheet.class);
        for (int i = 0; i < 3; i++) {
            Row mockRow = mock(Row.class);

            when(mockSheet.getRow(i)).thenReturn(mockRow);
            when(mockRow.getRowNum()).thenReturn(i);
            when(mockRow.iterator()).thenReturn(createMockCells().iterator());

            rows.add(mockRow);
        }
        return rows;
    }

    private List<Cell> createMockCells() {
       
        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            Cell mockCell = mock(org.apache.poi.ss.usermodel.Cell.class);
            when(mockCell.getColumnIndex()).thenReturn(i);
            switch (i) {
                case 1:
                    when(mockCell.getCellType()).thenReturn(CellType.STRING);
                    when(mockCell.getStringCellValue()).thenReturn("Category");
                    break;
                case 2:
                    when(mockCell.getCellType()).thenReturn(CellType.STRING);
                    when(mockCell.getStringCellValue()).thenReturn("Subcategory");
                    break;
                case 3:
                    when(mockCell.getCellType()).thenReturn(CellType.STRING);
                    when(mockCell.getStringCellValue()).thenReturn("Question " + i);
                    break;
                case 4:
                    when(mockCell.getCellType()).thenReturn(CellType.STRING);
                    when(mockCell.getStringCellValue()).thenReturn("Option A");
                    break;
                case 5:
                    when(mockCell.getCellType()).thenReturn(CellType.STRING);
                    when(mockCell.getStringCellValue()).thenReturn("Option B");
                    break;
                case 6:
                    when(mockCell.getCellType()).thenReturn(CellType.STRING);
                    when(mockCell.getStringCellValue()).thenReturn("Option C");
                    break;
                case 7:
                    when(mockCell.getCellType()).thenReturn(CellType.STRING);
                    when(mockCell.getStringCellValue()).thenReturn("Option D");
                    break;
                case 8:
                    when(mockCell.getCellType()).thenReturn(CellType.STRING);
                    when(mockCell.getStringCellValue()).thenReturn("A");
                    break;
                case 9:
                    when(mockCell.getCellType()).thenReturn(CellType.NUMERIC);
                    when(mockCell.getNumericCellValue()).thenReturn(1.0);
                    break;
                case 10:
                    when(mockCell.getCellType()).thenReturn(CellType.NUMERIC);
                    when(mockCell.getNumericCellValue()).thenReturn(0.5);
                    break;
                default:
                    break;
            }
            cells.add(mockCell);
        }
        return cells;
    }
}
