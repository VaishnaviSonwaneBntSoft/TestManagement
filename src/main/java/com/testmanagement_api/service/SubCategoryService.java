package com.testmanagement_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import com.testmanagement_api.dao.SubCategoryRepository;
import com.testmanagement_api.entity.Subcategory;
import com.testmanagement_api.exceptionhandler.CategoryNotFoundException;
import com.testmanagement_api.exceptionhandler.DataNotFoundException;
import com.testmanagement_api.exceptionhandler.DuplicateSubCategoryEntry;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SubCategoryService {

    @Autowired
    SubCategoryRepository subCategoryRepository;

    public Subcategory createSubcategory(Subcategory subcategory)
    {
        if(!subCategoryRepository.existsById(subcategory.getSubcategoryId()))
        {
           if(subCategoryRepository.existsById(subcategory.getCategory().getCategoryId()))
           {
                 return subCategoryRepository.save(subcategory);
           }else{
                CategoryNotFoundException exception = new CategoryNotFoundException("Category Not Found Which Foregin In Use");
                throw exception;
           }
          
        }else{
            DuplicateSubCategoryEntry duplicateSubCategoryEntry = new DuplicateSubCategoryEntry("Duplicate SubCategory");
            throw duplicateSubCategoryEntry;
        }
    }

    public List<Subcategory> getAllSubCategory()
    {
        return subCategoryRepository.findAll();
    }

    public Optional<Subcategory> getSubCategory(long subcategory_id)
    {
        if(subCategoryRepository.existsById(subcategory_id))
        {
            return subCategoryRepository.findById(subcategory_id);
        }else{
            DataNotFoundException exception = new DataNotFoundException("Id Not Found");
            throw exception;
        }
    }

    public Subcategory updateSubcategory(Subcategory subcategory,long subcategory_id)
    {
        if(subCategoryRepository.existsById(subcategory_id))
        {
            if(subCategoryRepository.existsById(subcategory.getCategory().getCategoryId()))
            {
                  return subCategoryRepository.save(subcategory);
            }else{
                 CategoryNotFoundException exception = new CategoryNotFoundException("Category Not Found Which Foregin In Use");
                 throw exception;
            }
        }else{
            DataNotFoundException exception = new DataNotFoundException("Id Not Found");
            throw exception;
        }
    }

    public void deleteSubcategory(long subcategory_id)
    {
        if(subCategoryRepository.existsById(subcategory_id))
        {
            subCategoryRepository.deleteById(subcategory_id);;
        }else{
            DataNotFoundException exception = new DataNotFoundException("Id Not Found");
            throw exception;
        }
    }

    public Subcategory getSubCategoryInstance(String subCatgeoryName , long category_id)
    {   log.info("Id From returned instance of subcategory is {}",category_id);//0
   
        Subcategory resultSubcategory = subCategoryRepository.getOneBysubCategoryName(subCatgeoryName);
        log.info("Id from which is present in subcategory {}", resultSubcategory.getCategory().getCategoryId());
       
        if(resultSubcategory.getCategory().getCategoryId()==category_id)
            return resultSubcategory;
        else
        return null;
    }

}
