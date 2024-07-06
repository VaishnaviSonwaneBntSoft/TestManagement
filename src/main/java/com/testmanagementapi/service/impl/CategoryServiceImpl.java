package com.testmanagementapi.service.impl;

import org.springframework.stereotype.Service;

import com.testmanagementapi.entity.Category;
import com.testmanagementapi.exception.DataNotFoundException;
import com.testmanagementapi.exception.DuplicateCategoryEntry;
import com.testmanagementapi.repository.CategoryRepository;
import com.testmanagementapi.service.CategoryService;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    private CategoryRepository categoryRepository;
    

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(Category category)
    {
        if(!categoryRepository.existsByCategoryName(category.getCategoryName()))
            return categoryRepository.save(category);

        throw new DuplicateCategoryEntry("Category Already Present ");
    }

    public List<Category> getAllCategory()
    {
        List<Category> categories = categoryRepository.findAll();
        if(!categories.isEmpty())
            return categories;
        
        throw new DataNotFoundException("Data Not Found");
    }

    public Optional<Category> getCategory(long categoryId)
    {
        if(categoryRepository.existsById(categoryId))
            return categoryRepository.findById(categoryId);

        throw new DataNotFoundException("Category Not Found By Provided Id");
    }

    public Category updateCategory(Category category , long categoryId)
    {
        if(categoryRepository.existsById(categoryId))
            return categoryRepository.save(category);

        throw new DataNotFoundException("Category Not Found By Provided Id");
    }

    public void deleteCategory(long categoryId)
    {
        if(categoryRepository.existsById(categoryId))
        {   categoryRepository.deleteById(categoryId);
        }else{
        throw new DataNotFoundException("Category Not Found By Provided Id");
        }
    }

    public Category getCategoryInstance(String categoryName)
    {
        if(categoryRepository.existsByCategoryName(categoryName))
            return categoryRepository.getOneByName(categoryName);
        else
            return null;
    }
}
