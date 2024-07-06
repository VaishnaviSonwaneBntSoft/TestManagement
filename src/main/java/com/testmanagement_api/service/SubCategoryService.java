package com.testmanagement_api.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.testmanagement_api.entity.Subcategory;
import com.testmanagement_api.exception.CategoryNotFoundException;
import com.testmanagement_api.exception.DataNotFoundException;
import com.testmanagement_api.exception.DuplicateSubCategoryEntry;
import com.testmanagement_api.repository.CategoryRepository;
import com.testmanagement_api.repository.SubCategoryRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SubCategoryService {

    private SubCategoryRepository subCategoryRepository;
    private CategoryRepository categoryRepository;

    public SubCategoryService(SubCategoryRepository subCategoryRepository, CategoryRepository categoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
        this.categoryRepository = categoryRepository;
    }

    public Subcategory createSubcategory(Subcategory subcategory)
    {
        if(!subCategoryRepository.existsBySubCategoryName(subcategory.getSubCategoryName()))
        {
           if(categoryRepository.existsByCategoryName(subcategory.getCategory().getCategoryName()))
           {    
                return subCategoryRepository.save(subcategory);
           }
                throw new CategoryNotFoundException("Category Not Found Which Foreign In Use");
        }
        throw new DuplicateSubCategoryEntry("Duplicate SubCategory");
    }

    public List<Subcategory> getAllSubCategory()
    {
        List<Subcategory> subcategories = subCategoryRepository.findAll();
        if(subcategories!=null)
            return subcategories;
       
        throw new DataNotFoundException("No Data Present In DataBase");
    }

    public Optional<Subcategory> getSubCategory(long subCategoryId)
    {
        if(subCategoryRepository.existsById(subCategoryId))
            return subCategoryRepository.findById(subCategoryId);
       
        throw new DataNotFoundException("Id Not Found");
    }

    public Subcategory updateSubcategory(Subcategory subcategory,long subCategoryId)
    {
        if(subCategoryRepository.existsById(subCategoryId))
        {
            if(subCategoryRepository.existsById(subcategory.getCategory().getCategoryId()))
                return subCategoryRepository.save(subcategory);
           
            throw new CategoryNotFoundException("Category Not Found Which Foreign In Use");
        }
        throw new DataNotFoundException("Id Not Found");
    }

    public void deleteSubcategory(long subCategoryId)
    {
        if(subCategoryRepository.existsById(subCategoryId))
        {    subCategoryRepository.deleteById(subCategoryId);
        }else{

            throw new DataNotFoundException("Id Not Found");
        }
        
    }

    public Subcategory getSubCategoryInstance(String subCategoryName , long categoryId)
    {   log.info("Id From returned instance of subcategory is {}",categoryId);
   
        Subcategory resultSubcategory = subCategoryRepository.getOneBySubCategoryName(subCategoryName);
        log.info("Id from which is present in subcategory {}", resultSubcategory.getCategory().getCategoryId());
       
        if(resultSubcategory.getCategory().getCategoryId()==categoryId)
            return resultSubcategory;

        return null;
    }

}
