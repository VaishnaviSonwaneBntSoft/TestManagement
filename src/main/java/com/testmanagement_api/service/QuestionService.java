package com.testmanagement_api.service;

import java.util.Iterator;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.testmanagement_api.entity.Category;
import com.testmanagement_api.entity.Subcategory;
import com.testmanagement_api.entity.QuestionModel;
import com.testmanagement_api.exceptionhandler.CategoryNotFoundException;
import com.testmanagement_api.exceptionhandler.DataNotFoundException;
import com.testmanagement_api.exceptionhandler.DuplicateEntries;

import com.testmanagement_api.exceptionhandler.DuplicatedDataException;
import com.testmanagement_api.exceptionhandler.SubcategoryNotFoundException;
import com.testmanagement_api.repository.QuestionRepository;
import com.testmanagement_api.repository.SubCategoryRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class QuestionService {

    private QuestionRepository tRepository;

    private SubCategoryRepository subCategoryRepository;

    private SubCategoryService subCategoryService;

    private CategoryService categoryService;

    

    public QuestionService(QuestionRepository tRepository, SubCategoryRepository subCategoryRepository,
            SubCategoryService subCategoryService, CategoryService categoryService) {

        this.tRepository = tRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.subCategoryService = subCategoryService;
        this.categoryService = categoryService;
    }

    public QuestionModel createMcqQuestion(QuestionModel model)
    {
        boolean result = tRepository.existsById(model.getQuestionId());
        if(!result && !tRepository.existsByQuestion(model.getQuestion()))
        {
            if(subCategoryRepository.existsById(model.getSubcategory().getSubcategoryId()))
                return tRepository.save(model);
                
            throw new SubcategoryNotFoundException("Subcategory Not Found Which Foreign In Use");
            
        }
        log.error("Data Already Present By Given Id");
        throw new DuplicatedDataException("This Question Already Present In Database");
    }

    public List<QuestionModel> getAllQuestionsData()
    {   
        List<QuestionModel> questionModels = tRepository.findAll();
        if(questionModels!=null)
        {
            log.info("Fetched all questions data");
            return questionModels;
        }
        throw new DataNotFoundException("No Data Found");
    }

    public QuestionModel updateQuestionData(QuestionModel model , long questionId)
    {
        if(tRepository.existsById(questionId))
        {
            if(subCategoryRepository.existsById(model.getSubcategory().getSubcategoryId()))
                return tRepository.save(model);
                
            throw new SubcategoryNotFoundException("Subcategory Not Found Which Foreign In Use");
        }
        throw new DataNotFoundException("Data Not Found For Provided Id");
    }

    public Optional<QuestionModel> getQuestionDataById(long questionId)
    {
        if(tRepository.existsById(questionId))
            return tRepository.findById(questionId);
        
        log.error("Data Not Found For Provided Id");
        throw new DataNotFoundException("Data Not Found For Provided ID");
    }

    public void deleteQuestionDataById(long questionId)
    {
        if(tRepository.existsById(questionId))
        {     tRepository.deleteById(questionId);
        }else{
            log.error("Data Not Found For Provided ID");
        throw new DataNotFoundException("Data Not Found For Provided ID");
        }
    }


    public void uploadBulkQuestions(MultipartFile multipartFile) throws EncryptedDocumentException, IOException
    {
        if(multipartFile.getOriginalFilename().endsWith(".xlsx"))
        {
            Workbook workbook = WorkbookFactory.create(multipartFile.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            List<String> duplicateQuestion = new ArrayList<>();
            List<QuestionModel> questions = new ArrayList<>();
           
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if (currentRow.getRowNum() == 0) {
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();
                QuestionModel testModel = new QuestionModel();

                long returnedCategoryId=0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    int columnIndex = currentCell.getColumnIndex();

                    Subcategory subcategory = new Subcategory();
                    Category returnedCategory=null;
                    

                    switch (columnIndex) {

                        case 1:
                       returnedCategory = categoryService.getCategoryInstance(currentCell.getStringCellValue());
                            if(returnedCategory!=null)
                            {
                                subcategory.setCategory(returnedCategory);
                                returnedCategoryId=returnedCategory.getCategoryId();
                                log.info("Id of Category "+returnedCategoryId); 
                            }else{
                                throw new CategoryNotFoundException("Given Category Not Present");
                            }
                            
                                
                        break;
                        case 2:
                        Subcategory returnedSubcategory = subCategoryService.getSubCategoryInstance(currentCell.getStringCellValue() , returnedCategoryId);
                            if(returnedSubcategory!=null)
                                testModel.setSubcategory(returnedSubcategory);
                            else
                                throw new SubcategoryNotFoundException("Not Found Subcategory with foreign key of given category");

                        break;
                        case 3:
                            testModel.setQuestion(currentCell.getStringCellValue());
                            break;
                        case 4:
                            testModel.setOptionOne(currentCell.getStringCellValue());
                            break;
                        case 5:
                            testModel.setOptionTwo(currentCell.getStringCellValue());
                            break;
                        case 6:
                            testModel.setOptionThree(currentCell.getStringCellValue());
                            break;
                        case 7:
                            testModel.setOptionFour(currentCell.getStringCellValue());
                            break;
                        case 8:
                            testModel.setCorrectOption(currentCell.getStringCellValue());
                            break;
                            case 9:
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                testModel.setPositiveMark(String.valueOf(currentCell.getNumericCellValue()));
                            } else if (currentCell.getCellType() == CellType.STRING) {
                                testModel.setPositiveMark(currentCell.getStringCellValue());
                            }
                            break;
                        case 10:
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                testModel.setNegativeMark(String.valueOf(currentCell.getNumericCellValue()));
                            } else if (currentCell.getCellType() == CellType.STRING) {
                                testModel.setNegativeMark(currentCell.getStringCellValue());
                            }
                            break;
                        default:
                            break;
                    }
                }
                
                if(tRepository.existsByQuestion(testModel.getQuestion()))
                    duplicateQuestion.add(testModel.getQuestion());
                else
                    questions.add(testModel);
        }
        workbook.close();
        if(duplicateQuestion.isEmpty())
            tRepository.saveAll(questions);
        else
            throw new DuplicateEntries(duplicateQuestion);
    }
}

}