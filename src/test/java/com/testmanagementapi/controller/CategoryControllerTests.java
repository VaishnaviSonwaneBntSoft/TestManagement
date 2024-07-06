package com.testmanagementapi.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.testmanagementapi.controller.CategoryController;
import com.testmanagementapi.entity.Category;
import com.testmanagementapi.response.SuccessResponse;
import com.testmanagementapi.service.impl.CategoryServiceImpl;

class CategoryControllerTests {

    @Mock
    private CategoryServiceImpl categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCategory_Success() {
       
        Category category = new Category();
        category.setCategoryId(1L);
        category.setCategoryName("Test Category");

        when(categoryService.createCategory(category)).thenReturn(category);

        ResponseEntity<SuccessResponse> responseEntity = categoryController.createCategory(category);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("New Category Is Created", responseEntity.getBody().getMessage());
        assertEquals(category, responseEntity.getBody().getModuleData());
    }

    @Test
    void testCreateCategory_DuplicateData() {
   
        Category category = new Category();
        category.setCategoryId(1L);
        category.setCategoryName("Test Category");

        when(categoryService.createCategory(category)).thenThrow(new RuntimeException("Duplicate Data"));

        ResponseEntity<SuccessResponse> responseEntity = categoryController.createCategory(category);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Duplicate Data", responseEntity.getBody().getMessage());
        assertNull(responseEntity.getBody().getModuleData());
    }

    @Test
    void testGetAllCategory() {
 
        List<Category> categoryList = new ArrayList<>();
        Category category1 = new Category();
        category1.setCategoryId(1L);
        category1.setCategoryName("Category 1");

        Category category2 = new Category();
        category2.setCategoryId(2L);
        category2.setCategoryName("Category 2");

        categoryList.add(category1);
        categoryList.add(category2);

        when(categoryService.getAllCategory()).thenReturn(categoryList);

        ResponseEntity<SuccessResponse> responseEntity = categoryController.getAllCategory();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("All Categories Retrieved", responseEntity.getBody().getMessage());
        assertEquals(categoryList, responseEntity.getBody().getModuleData());
    }

    @Test
    void testGetCategory_Success() {
       
        long categoryId = 1L;
        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setCategoryName("Test Category");

        when(categoryService.getCategory(categoryId)).thenReturn(Optional.of(category));

        ResponseEntity<SuccessResponse> responseEntity = categoryController.getCategory(categoryId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Category Retrieved By Specified Id", responseEntity.getBody().getMessage());
        assertEquals(Optional.of(category), responseEntity.getBody().getModuleData());
    }

    @Test
    void testGetCategory_IdNotFound() {
    
        long categoryId = 1L;

        when(categoryService.getCategory(categoryId)).thenThrow(new RuntimeException("Id Not Found"));

        ResponseEntity<SuccessResponse> responseEntity = categoryController.getCategory(categoryId);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Id Not Found", responseEntity.getBody().getMessage());
    }

    @Test
    void testUpdateCategory_Success() {
 
        long categoryId = 1L;
        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setCategoryName("Updated Category");

        when(categoryService.updateCategory(category, categoryId)).thenReturn(category);

        ResponseEntity<SuccessResponse> responseEntity = categoryController.putCategory(categoryId, category);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Category Updated", responseEntity.getBody().getMessage());
        assertEquals(category, responseEntity.getBody().getModuleData());
    }

    @Test
    void testUpdateCategory_IdNotFound() {
 
        long categoryId = 1L;
        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setCategoryName("Updated Category");

        when(categoryService.updateCategory(category, categoryId)).thenThrow(new RuntimeException("Id Not Found"));

        ResponseEntity<SuccessResponse> responseEntity = categoryController.putCategory(categoryId, category);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Id Not Found", responseEntity.getBody().getMessage());
    }

    @Test
    void testDeleteCategory_Success() {
    
        long categoryId = 1L;

        ResponseEntity<SuccessResponse> responseEntity = categoryController.deleteCategory(categoryId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Category Deleted By Specified Id", responseEntity.getBody().getMessage());
        assertNull(responseEntity.getBody().getModuleData());
    }

    @Test
    void testDeleteCategory_IdNotFound() {
       
        long categoryId = 1L;

        doThrow(new RuntimeException("Id Not Found")).when(categoryService).deleteCategory(categoryId);

        ResponseEntity<SuccessResponse> responseEntity = categoryController.deleteCategory(categoryId);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Id Not Found", responseEntity.getBody().getMessage());
    }
}

