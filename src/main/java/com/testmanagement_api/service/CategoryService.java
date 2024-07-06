package com.testmanagement_api.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.testmanagement_api.entity.Category;
import com.testmanagement_api.exception.DataNotFoundException;
import com.testmanagement_api.exception.DuplicateCategoryEntry;
import com.testmanagement_api.repository.CategoryRepository;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;
    

    public CategoryService(CategoryRepository categoryRepository) {
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
