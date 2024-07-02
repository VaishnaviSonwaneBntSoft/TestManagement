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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.testmanagement_api.dao.SubCategoryRepository;
import com.testmanagement_api.dao.QuestionRepository;
import com.testmanagement_api.entity.Category;
import com.testmanagement_api.entity.Subcategory;
import com.testmanagement_api.entity.QuestionModel;
import com.testmanagement_api.exceptionhandler.DataNotFoundException;
import com.testmanagement_api.exceptionhandler.DuplicateEntries;
import com.testmanagement_api.exceptionhandler.DuplicatedDataException;
import com.testmanagement_api.exceptionhandler.SubcategoryNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class QuestionService {

    @Autowired
    QuestionRepository tRepository;

    @Autowired
    SubCategoryRepository subCategoryRepository;

    @Autowired
    SubCategoryService subCategoryService;

    @Autowired
    CategoryService categoryService;

    public QuestionModel CreateMcqQuestion(QuestionModel model)
    {
        boolean result = tRepository.existsById(model.getQuestion_id());
        if(!result && tRepository.existsByQuestion(model.getQuestion()))
        {
            if(subCategoryRepository.existsById(model.getSubcategory().getSubcategoryId()))
            {
                return tRepository.save(model);

            }else{
                SubcategoryNotFoundException exception = new SubcategoryNotFoundException("Subcategory Not Found Which Foregin In Use");
                throw exception;
            }
        }else{
            log.error("Data Already Present By Given Id");
            DuplicatedDataException exception = new DuplicatedDataException("This Question Already Present In Database");
            throw exception;
        }
    }

    public List<QuestionModel> GetAllQuestionsData()
    {   
        log.info("Fetching all questions data");
        return tRepository.findAll();
    }

    public QuestionModel updateQuestionData(QuestionModel model , long question_id)
    {
        boolean result = tRepository.existsById(question_id);
        if(result)
        {
            if(subCategoryRepository.existsById(model.getSubcategory().getSubcategoryId()))
            {
                return tRepository.save(model);
            }else{
                SubcategoryNotFoundException exception = new SubcategoryNotFoundException("Subcategory Not Found Which Foregin In Use");
                throw exception;
            }
        }else{
            
            DataNotFoundException exception = new DataNotFoundException("Data Not Found For Provided ID");
            throw exception;
        }
    }

    public Optional<QuestionModel> getQuestionDataById(long question_id)
    {

        boolean result = tRepository.existsById(question_id);
        if(result)
        {
            return tRepository.findById(question_id);
        }else{
            log.error("Data Not Found For Provided ID");
            DataNotFoundException exception = new DataNotFoundException("Data Not Found For Provided ID");
            throw exception;
        }
    }

    public void deleteQuestionDataById(long question_id)
    {
        boolean result = tRepository.existsById(question_id);
        if(result)
        {
            tRepository.deleteById(question_id);
        }else{
            log.error("Data Not Found For Provided ID");
            DataNotFoundException exception = new DataNotFoundException("Data Not Found For Provided ID");
            throw exception;
        }
    }

   

    public void uploadBulkQuestions(MultipartFile multipartFile) throws EncryptedDocumentException, IOException
    {
        if(multipartFile.getOriginalFilename().endsWith(".xlsx"))
        {
            Workbook workbook = WorkbookFactory.create(multipartFile.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            List<QuestionModel> questions = new ArrayList<QuestionModel>();

            List<String> duplicateQuestion = new ArrayList<String>();
            List<String> duplicCategories = new ArrayList<String>();
            List<String> duplicSubcategories = new ArrayList<String>();

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
                                log.info("Id of Catgeory "+returnedCategoryId);   
                            }
                            else
                            {
                                duplicCategories.add(returnedCategory.getCategoryName());
                            }         
                        break;

                        case 2:
                        Subcategory returnedSubcategory = subCategoryService.getSubCategoryInstance(currentCell.getStringCellValue() , returnedCategoryId);
                            if(returnedSubcategory!=null)
                                testModel.setSubcategory(returnedSubcategory);
                            else
                                duplicSubcategories.add(returnedSubcategory.getSubCategoryName());
                            

                        break;
                        case 3:
                            testModel.setQuestion(currentCell.getStringCellValue());
                            break;
                        case 4:
                            testModel.setOption_one(currentCell.getStringCellValue());
                            break;
                        case 5:
                            testModel.setOption_two(currentCell.getStringCellValue());
                            break;
                        case 6:
                            testModel.setOption_three(currentCell.getStringCellValue());
                            break;
                        case 7:
                            testModel.setOption_four(currentCell.getStringCellValue());
                            break;
                        case 8:
                            testModel.setCorrect_option(currentCell.getStringCellValue());
                            break;
                            case 9:
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                testModel.setPositive_mark(String.valueOf(currentCell.getNumericCellValue()));
                            } else if (currentCell.getCellType() == CellType.STRING) {
                                testModel.setPositive_mark(currentCell.getStringCellValue());
                            }
                            break;
                        case 10:
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                testModel.setNegative_mark(String.valueOf(currentCell.getNumericCellValue()));
                            } else if (currentCell.getCellType() == CellType.STRING) {
                                testModel.setNegative_mark(currentCell.getStringCellValue());
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
        if(duplicateQuestion.size()==0)
            if(duplicCategories.size()==0)
                if(duplicSubcategories.size()==0)
                    tRepository.saveAll(questions);
                else
                    throw new DuplicateEntries(duplicSubcategories);
            else
                throw new DuplicateEntries(duplicCategories);
        else
            throw new DuplicateEntries(duplicateQuestion);
    }
}

    

}