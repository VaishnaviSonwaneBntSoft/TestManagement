package com.testmanagement_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testmanagement_api.dao.SubCategoryRepository;
import com.testmanagement_api.dao.TestManagementRepository;
import com.testmanagement_api.entity.TestModel;
import com.testmanagement_api.exceptionhandler.DataNotFoundException;
import com.testmanagement_api.exceptionhandler.DuplicatedDataException;
import com.testmanagement_api.exceptionhandler.SubcategoryNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TestManagementService {

    @Autowired
    TestManagementRepository tRepository;

    @Autowired
    SubCategoryRepository subCategoryRepository;

    public TestModel CreateMcqQuestion(TestModel model)
    {
        boolean result = tRepository.existsById(model.getQuestion_id());
        if(!result)
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
            DuplicatedDataException exception = new DuplicatedDataException("Data Already Present By Given Id");
            throw exception;
        }
    }

    public List<TestModel> GetAllQuestionsData()
    {   
        log.info("Fetching all questions data");
        return tRepository.findAll();
    }

    public TestModel updateQuestionData(TestModel model , long question_id)
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

    public Optional<TestModel> getQuestionDataById(long question_id)
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
}
