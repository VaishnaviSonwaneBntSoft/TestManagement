package com.testmanagementapi.service.impl;

import org.springframework.stereotype.Service;

import com.testmanagementapi.entity.Subcategory;
import com.testmanagementapi.exception.CategoryNotFoundException;
import com.testmanagementapi.exception.DataNotFoundException;
import com.testmanagementapi.exception.DuplicateSubCategoryEntry;
import com.testmanagementapi.repository.CategoryRepository;
import com.testmanagementapi.repository.SubCategoryRepository;
import com.testmanagementapi.service.SubCategoryService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SubCategoryServiceImpl implements SubCategoryService{

    private SubCategoryRepository subCategoryRepository;
    private CategoryRepository categoryRepository;

    public SubCategoryServiceImpl(SubCategoryRepository subCategoryRepository, CategoryRepository categoryRepository) {
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
