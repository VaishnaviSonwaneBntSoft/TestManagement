package com.testmanagementapi.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.testmanagementapi.entity.Category;
import com.testmanagementapi.response.SuccessResponse;
import com.testmanagementapi.service.impl.CategoryServiceImpl;

class CategoryControllerTest {

    private CategoryServiceImpl categoryService;
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        categoryService = mock(CategoryServiceImpl.class);
        categoryController = new CategoryController(categoryService);
    }

    @Test
    void testCreateCategory() {
     
        Category category = new Category();
        category.setCategoryName("Test Category");

        when(categoryService.createCategory(any(Category.class))).thenReturn(category);

        ResponseEntity<SuccessResponse> responseEntity = categoryController.createCategory(category);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("New Category Is Created", responseEntity.getBody().getMessage());
        assertEquals(category, responseEntity.getBody().getModuleData());

        verify(categoryService, times(1)).createCategory(any(Category.class));
    }

    @Test
    void testGetAllCategory() {
        
        Category category = new Category();
        category.setCategoryId(1l);
        category.setCategoryName("Updated Category");
        Category category2 = new Category();
        category.setCategoryId(2l);
        category.setCategoryName(" Category");

        List<Category> categoryList = new ArrayList<Category>();
        categoryList.add(category2);
        categoryList.add(category);

        when(categoryService.getAllCategory()).thenReturn(categoryList);

        ResponseEntity<SuccessResponse> responseEntity = categoryController.getAllCategory();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("All Categories Retrieved", responseEntity.getBody().getMessage());
        assertEquals(categoryList, responseEntity.getBody().getModuleData());

        verify(categoryService, times(1)).getAllCategory();
    }

    @Test
    void testGetCategory() {
        long categoryId = 1L;

        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setCategoryName("Updated Category");

        when(categoryService.getCategory(categoryId)).thenReturn(Optional.of(category));

        ResponseEntity<SuccessResponse> responseEntity = categoryController.getCategory(categoryId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Category Retrieved By Specified Id", responseEntity.getBody().getMessage());
        assertEquals(Optional.of(category), responseEntity.getBody().getModuleData()); 

        verify(categoryService, times(1)).getCategory(categoryId);
    }

    @Test
    void testPutCategory() {
        long categoryId = 1L;
        
        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setCategoryName("Updated Category");

        when(categoryService.updateCategory(any(Category.class), eq(categoryId))).thenReturn(category);

        ResponseEntity<SuccessResponse> responseEntity = categoryController.putCategory(categoryId, category);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Category Updated", responseEntity.getBody().getMessage());
        assertEquals(category, responseEntity.getBody().getModuleData());

        verify(categoryService, times(1)).updateCategory(any(Category.class), eq(categoryId));
    }

    @Test
    void testDeleteCategory() {
        long categoryId = 1L;

        ResponseEntity<SuccessResponse> responseEntity = categoryController.deleteCategory(categoryId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Category Deleted By Specified Id", responseEntity.getBody().getMessage());
        assertNull(responseEntity.getBody().getModuleData());

        verify(categoryService, times(1)).deleteCategory(categoryId);
    }
}


