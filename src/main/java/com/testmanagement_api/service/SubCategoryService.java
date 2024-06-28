package com.testmanagement_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.testmanagement_api.dao.CategoryRepository;
import com.testmanagement_api.dao.SubCategoryRepository;
import com.testmanagement_api.entity.Subcategory;
import com.testmanagement_api.exceptionhandler.CategoryNotFoundException;
import com.testmanagement_api.exceptionhandler.DataNotFoundException;
import com.testmanagement_api.exceptionhandler.DuplicateSubCategoryEntry;

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

}
