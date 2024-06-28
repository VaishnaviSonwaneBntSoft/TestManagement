package com.testmanagement_api.service;

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

import com.testmanagement_api.dao.CategoryRepository;
import com.testmanagement_api.entity.Category;
import com.testmanagement_api.exceptionhandler.DataNotFoundException;
import com.testmanagement_api.exceptionhandler.DuplicateCategoryEntry;

public class CategoryServiceTests {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCategory_Success() {

        Category category = new Category();
        category.setCategoryId(1L);
        category.setCategoryName("Test Category");

        when(categoryRepository.existsById(1L)).thenReturn(false);
        when(categoryRepository.save(category)).thenReturn(category);

        Category result = categoryService.createCategory(category);

        assertNotNull(result);
        assertEquals(category.getCategoryId(), result.getCategoryId());
        assertEquals(category.getCategoryName(), result.getCategoryName());
    }

    @Test
    public void testCreateCategory_DuplicateCategory() {
        
        Category category = new Category();
        category.setCategoryId(1L);
        category.setCategoryName("Test Category");

        when(categoryRepository.existsById(1L)).thenReturn(true);

        assertThrows(DuplicateCategoryEntry.class, () -> {
            categoryService.createCategory(category);
        });
    }

    @Test
    public void testGetAllCategory() {
       
        List<Category> categoryList = new ArrayList<>();
        Category category1 = new Category();
        category1.setCategoryId(1L);
        category1.setCategoryName("Category 1");

        Category category2 = new Category();
        category2.setCategoryId(2L);
        category2.setCategoryName("Category 2");

        categoryList.add(category1);
        categoryList.add(category2);

        when(categoryRepository.findAll()).thenReturn(categoryList);

        List<Category> result = categoryService.getAllCategory();
        
        assertEquals(2, result.size());
        assertEquals(categoryList, result);
    }

    @Test
    public void testGetCategory_Success() {
        
        Category category = new Category();
        category.setCategoryId(1L);
        category.setCategoryName("Test Category");

        when(categoryRepository.existsById(1L)).thenReturn(true);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Optional<Category> result = categoryService.getCategory(1L);

        assertTrue(result.isPresent());
        assertEquals(category.getCategoryId(), result.get().getCategoryId());
        assertEquals(category.getCategoryName(), result.get().getCategoryName());
    }

    @Test
    public void testGetCategory_CategoryNotFound() {
       
        when(categoryRepository.existsById(1L)).thenReturn(false);

        assertThrows(DataNotFoundException.class, () -> {
            categoryService.getCategory(1L);
        });
    }

    @Test
    public void testUpdateCategory_Success() {
        
        Category categoryToUpdate = new Category();
        categoryToUpdate.setCategoryId(1L);
        categoryToUpdate.setCategoryName("Updated Category");

        when(categoryRepository.existsById(1L)).thenReturn(true);
        when(categoryRepository.save(categoryToUpdate)).thenReturn(categoryToUpdate);

        Category result = categoryService.updateCategory(categoryToUpdate, 1L);

        assertNotNull(result);
        assertEquals(categoryToUpdate.getCategoryId(), result.getCategoryId());
        assertEquals(categoryToUpdate.getCategoryName(), result.getCategoryName());
    }

    @Test
    public void testUpdateCategory_CategoryNotFound() {
       
        Category categoryToUpdate = new Category();
        categoryToUpdate.setCategoryId(1L);
        categoryToUpdate.setCategoryName("Updated Category");

        when(categoryRepository.existsById(1L)).thenReturn(false);

        assertThrows(DataNotFoundException.class, () -> {
            categoryService.updateCategory(categoryToUpdate, 1L);
        });
    }

    @Test
    public void testDeleteCategory_Success() {
       
        when(categoryRepository.existsById(1L)).thenReturn(true);

        categoryService.deleteCategory(1L);

        verify(categoryRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteCategory_CategoryNotFound() {
        
        when(categoryRepository.existsById(1L)).thenReturn(false);

        assertThrows(DataNotFoundException.class, () -> {
            categoryService.deleteCategory(1L);
        });
    }
}

