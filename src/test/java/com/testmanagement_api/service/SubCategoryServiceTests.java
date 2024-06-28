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

import com.testmanagement_api.dao.SubCategoryRepository;
import com.testmanagement_api.entity.Category;
import com.testmanagement_api.entity.Subcategory;
import com.testmanagement_api.exceptionhandler.DataNotFoundException;
import com.testmanagement_api.exceptionhandler.DuplicateSubCategoryEntry;

public class SubCategoryServiceTests {

    @Mock
    private SubCategoryRepository subCategoryRepository;

    @InjectMocks
    private SubCategoryService subCategoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateSubcategory_Success() {
   
        Category category = new Category();
        category.setCategoryId(1L);
        category.setCategoryName("Test Category");
        

        Subcategory subcategory = new Subcategory();
        subcategory.setSubcategoryId(11L);
        subcategory.setSubCategoryName("Test Subcategory");
        subcategory.setCategory(category);

        when(subCategoryRepository.existsById(11L)).thenReturn(false);
        when(subCategoryRepository.existsById(1L)).thenReturn(true);
        when(subCategoryRepository.save(subcategory)).thenReturn(subcategory);

        Subcategory result = subCategoryService.createSubcategory(subcategory);

        assertNotNull(result);
        assertEquals(subcategory.getSubcategoryId(), result.getSubcategoryId());
        assertEquals(subcategory.getSubCategoryName(), result.getSubCategoryName());
    }

    @Test
    public void testCreateSubcategory_DuplicateSubcategory() {
      
        Category category = new Category();
        category.setCategoryId(1L);
        category.setCategoryName("Test Category");

        Subcategory subcategory = new Subcategory();
        subcategory.setSubcategoryId(1L);
        subcategory.setSubCategoryName("Test Subcategory");
        subcategory.setCategory(category);

        when(subCategoryRepository.existsById(1L)).thenReturn(true);

        assertThrows(DuplicateSubCategoryEntry.class, () -> {
            subCategoryService.createSubcategory(subcategory);
        });
    }

    @Test
    public void testGetAllSubCategory() {
    
        List<Subcategory> subcategoryList = new ArrayList<>();
        Category category = new Category();
        category.setCategoryId(1L);
        category.setCategoryName("Test Category");

        Subcategory subcategory1 = new Subcategory();
        subcategory1.setSubcategoryId(1L);
        subcategory1.setSubCategoryName("Subcategory 1");
        subcategory1.setCategory(category);

        Subcategory subcategory2 = new Subcategory();
        subcategory2.setSubcategoryId(2L);
        subcategory2.setSubCategoryName("Subcategory 2");
        subcategory2.setCategory(category);

        subcategoryList.add(subcategory1);
        subcategoryList.add(subcategory2);

        when(subCategoryRepository.findAll()).thenReturn(subcategoryList);

        List<Subcategory> result = subCategoryService.getAllSubCategory();

        assertEquals(2, result.size());
        assertEquals(subcategoryList, result);
    }

    @Test
    public void testGetSubCategory_Success() {
     
        Category category = new Category();
        category.setCategoryId(1L);
        category.setCategoryName("Test Category");

        Subcategory subcategory = new Subcategory();
        subcategory.setSubcategoryId(1L);
        subcategory.setSubCategoryName("Test Subcategory");
        subcategory.setCategory(category);

        when(subCategoryRepository.existsById(1L)).thenReturn(true);
        when(subCategoryRepository.findById(1L)).thenReturn(Optional.of(subcategory));

        Optional<Subcategory> result = subCategoryService.getSubCategory(1L);

        assertTrue(result.isPresent());
        assertEquals(subcategory.getSubcategoryId(), result.get().getSubcategoryId());
        assertEquals(subcategory.getSubCategoryName(), result.get().getSubCategoryName());
    }

    @Test
    public void testGetSubCategory_SubcategoryNotFound() {
  
        when(subCategoryRepository.existsById(1L)).thenReturn(false);

        assertThrows(DataNotFoundException.class, () -> {
            subCategoryService.getSubCategory(1L);
        });
    }

    @Test
    public void testUpdateSubcategory_Success() {
    
        Category category = new Category();
        category.setCategoryId(1L);
        category.setCategoryName("Test Category");

        Subcategory subcategoryToUpdate = new Subcategory();
        subcategoryToUpdate.setSubcategoryId(1L);
        subcategoryToUpdate.setSubCategoryName("Updated Subcategory");
        subcategoryToUpdate.setCategory(category);

        when(subCategoryRepository.existsById(1L)).thenReturn(true);
        when(subCategoryRepository.existsById(1L)).thenReturn(true);
        when(subCategoryRepository.save(subcategoryToUpdate)).thenReturn(subcategoryToUpdate);

        Subcategory result = subCategoryService.updateSubcategory(subcategoryToUpdate, 1L);

        assertNotNull(result);
        assertEquals(subcategoryToUpdate.getSubcategoryId(), result.getSubcategoryId());
        assertEquals(subcategoryToUpdate.getSubCategoryName(), result.getSubCategoryName());
    }

    @Test
    public void testUpdateSubcategory_SubcategoryNotFound() {
    
        Category category = new Category();
        category.setCategoryId(1L);
        category.setCategoryName("Test Category");

        Subcategory subcategoryToUpdate = new Subcategory();
        subcategoryToUpdate.setSubcategoryId(1L);
        subcategoryToUpdate.setSubCategoryName("Updated Subcategory");
        subcategoryToUpdate.setCategory(category);

        when(subCategoryRepository.existsById(1L)).thenReturn(false);

        assertThrows(DataNotFoundException.class, () -> {
            subCategoryService.updateSubcategory(subcategoryToUpdate, 1L);
        });
    }

    @Test
    public void testDeleteSubcategory_Success() {
       
        when(subCategoryRepository.existsById(1L)).thenReturn(true);

        subCategoryService.deleteSubcategory(1L);

        verify(subCategoryRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteSubcategory_SubcategoryNotFound() {
    
        when(subCategoryRepository.existsById(1L)).thenReturn(false);

        assertThrows(DataNotFoundException.class, () -> {
            subCategoryService.deleteSubcategory(1L);
        });
    }
}

