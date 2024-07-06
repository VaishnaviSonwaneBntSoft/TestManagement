package com.testmanagementapi.service;

import com.testmanagementapi.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Category createCategory(Category category);

    List<Category> getAllCategory();

    Optional<Category> getCategory(long categoryId);

    Category updateCategory(Category category, long categoryId);

    void deleteCategory(long categoryId);

    Category getCategoryInstance(String categoryName);
}

