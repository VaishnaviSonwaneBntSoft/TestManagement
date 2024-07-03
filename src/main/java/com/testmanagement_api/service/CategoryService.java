package com.testmanagement_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import com.testmanagement_api.dao.CategoryRepository;
import com.testmanagement_api.entity.Category;
import com.testmanagement_api.exceptionhandler.DataNotFoundException;
import com.testmanagement_api.exceptionhandler.DuplicateCategoryEntry;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public Category createCategory(Category category)
    {
        boolean result = categoryRepository.existsBycategoryName(category.getCategoryName());
        if(!result)
        {
            System.out.println(category);
            return categoryRepository.save(category);
        }else{
            DuplicateCategoryEntry duplicateCategoryEntry = new DuplicateCategoryEntry("Category Already Present ");
            throw duplicateCategoryEntry;
        }

    }

    public List<Category> getAllCategory()
    {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategory(long category_id)
    {
        boolean result = categoryRepository.existsById(category_id);
        if(result)
        {
            return categoryRepository.findById(category_id);
        }else{
            DataNotFoundException exception = new DataNotFoundException("Category Not Found By Provided Id");
            throw exception;
        }
    }

    public Category updateCategory(Category category , long category_id)
    {
        if(categoryRepository.existsById(category_id))
        {
            return categoryRepository.save(category);
        }else{
            DataNotFoundException exception = new DataNotFoundException("Category Not Found By Provided Id");
            throw exception;
        }
    }

    public void deleteCategory(long category_id)
    {
        if(categoryRepository.existsById(category_id))
        {
            categoryRepository.deleteById(category_id);
        }else{
            DataNotFoundException exception = new DataNotFoundException("Category Not Found By Provided Id");
            throw exception;
        }
    }

    public Category getCategoryInstance(String categoryName)
    {
        if(categoryRepository.existsBycategoryName(categoryName))
            return categoryRepository.getOneByName(categoryName);
        else
        return null;
    }
}
