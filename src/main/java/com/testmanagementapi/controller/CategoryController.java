package com.testmanagementapi.controller;

import org.springframework.web.bind.annotation.RestController;
import com.testmanagementapi.entity.Category;
import com.testmanagementapi.response.SuccessResponse;
import com.testmanagementapi.service.impl.CategoryServiceImpl;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@Validated
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private CategoryServiceImpl categoryService;

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<SuccessResponse> createCategory(@Valid @RequestBody Category category) {
        Category returnedCategory = categoryService.createCategory(category);
        SuccessResponse successResponse = new SuccessResponse("New Category Is Created", 200, returnedCategory);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<SuccessResponse> getAllCategory() {

        List<Category> categoryList = categoryService.getAllCategory();
        SuccessResponse successResponse = new SuccessResponse("All Categories Retrieved", 200, categoryList);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @GetMapping("/{Category-id}")
    public ResponseEntity<SuccessResponse> getCategory(@PathVariable("Category-id") long categoryId) {

        Optional<Category> optionalCategory = categoryService.getCategory(categoryId);
        SuccessResponse successResponse = new SuccessResponse("Category Retrieved By Specified Id", 200,
                optionalCategory);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PutMapping("/{Category-id}")
    public ResponseEntity<SuccessResponse> putCategory(@PathVariable("Category-id") long categoryId,
           @Valid @RequestBody Category category) {

        Category updatedCategory = categoryService.updateCategory(category, categoryId);
        SuccessResponse successResponse = new SuccessResponse("Category Updated", 200, updatedCategory);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{Category-id}")
    public ResponseEntity<SuccessResponse> deleteCategory(@PathVariable("Category-id") long categoryId) {
        categoryService.deleteCategory(categoryId);
        SuccessResponse successResponse = new SuccessResponse("Category Deleted By Specified Id", 200, null);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }
}
