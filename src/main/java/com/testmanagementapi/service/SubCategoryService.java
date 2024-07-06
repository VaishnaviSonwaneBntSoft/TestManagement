package com.testmanagementapi.service;

import com.testmanagementapi.entity.Subcategory;

import java.util.List;
import java.util.Optional;

public interface SubCategoryService {

    Subcategory createSubcategory(Subcategory subcategory);

    List<Subcategory> getAllSubCategory();

    Optional<Subcategory> getSubCategory(long subCategoryId);

    Subcategory updateSubcategory(Subcategory subcategory, long subCategoryId);

    void deleteSubcategory(long subCategoryId);

    Subcategory getSubCategoryInstance(String subCategoryName, long categoryId);
}

