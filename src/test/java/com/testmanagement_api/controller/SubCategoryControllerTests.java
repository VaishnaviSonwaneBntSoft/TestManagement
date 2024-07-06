package com.testmanagement_api.controller;

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

import com.testmanagement_api.entity.Subcategory;
import com.testmanagement_api.response.SuccessResponse;
import com.testmanagement_api.service.SubCategoryService;

class SubCategoryControllerTests {

    @Mock
    private SubCategoryService subCategoryService;

    @InjectMocks
    private SubCategoryController subCategoryController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSubCategory_Success() {
       
        Subcategory subcategory = new Subcategory();
        subcategory.setSubcategoryId(1L);
        subcategory.setSubCategoryName("Test Subcategory");

        when(subCategoryService.createSubcategory(subcategory)).thenReturn(subcategory);

        ResponseEntity<SuccessResponse> responseEntity = subCategoryController.createSubCategory(subcategory);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("SubCategory Created", responseEntity.getBody().getMessage());
        assertEquals(subcategory, responseEntity.getBody().getModuleData());
    }

    @Test
    void testCreateSubCategory_DuplicateData() {
    
        Subcategory subcategory = new Subcategory();
        subcategory.setSubcategoryId(1L);
        subcategory.setSubCategoryName("Test Subcategory");

        when(subCategoryService.createSubcategory(subcategory)).thenThrow(new RuntimeException("Duplicate Data"));

        ResponseEntity<SuccessResponse> responseEntity = subCategoryController.createSubCategory(subcategory);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Duplicate Data", responseEntity.getBody().getMessage());
        assertNull(responseEntity.getBody().getModuleData());
    }

    @Test
    void testGetAllSubCategory() {
    
        List<Subcategory> subcategoryList = new ArrayList<>();
        Subcategory subcategory1 = new Subcategory();
        subcategory1.setSubcategoryId(1L);
        subcategory1.setSubCategoryName("Subcategory 1");

        Subcategory subcategory2 = new Subcategory();
        subcategory2.setSubcategoryId(2L);
        subcategory2.setSubCategoryName("Subcategory 2");

        subcategoryList.add(subcategory1);
        subcategoryList.add(subcategory2);

        when(subCategoryService.getAllSubCategory()).thenReturn(subcategoryList);

        ResponseEntity<SuccessResponse> responseEntity = subCategoryController.getAllSubCategory();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("All Subcategory Data Retrieved", responseEntity.getBody().getMessage());
        assertEquals(subcategoryList, responseEntity.getBody().getModuleData());
    }

    @Test
    void testGetSubCategory_Success() {
     
        long subcategoryId = 1L;
        Subcategory subcategory = new Subcategory();
        subcategory.setSubcategoryId(subcategoryId);
        subcategory.setSubCategoryName("Test Subcategory");

        when(subCategoryService.getSubCategory(subcategoryId)).thenReturn(Optional.of(subcategory));

        ResponseEntity<SuccessResponse> responseEntity = subCategoryController.getSubCategory(subcategoryId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Subcategory Data Retrieved", responseEntity.getBody().getMessage());
        assertEquals(subcategory, responseEntity.getBody().getModuleData());
    }

    @Test
    void testGetSubCategory_IdNotFound() {
       
        long subcategoryId = 1L;

        when(subCategoryService.getSubCategory(subcategoryId)).thenThrow(new RuntimeException("Id Not Found"));

        ResponseEntity<SuccessResponse> responseEntity = subCategoryController.getSubCategory(subcategoryId);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Id Not Found", responseEntity.getBody().getMessage());
        assertNull(responseEntity.getBody().getModuleData());
    }

    @Test
    void testUpdateSubCategory_Success() {
       
        long subcategoryId = 1L;
        Subcategory subcategory = new Subcategory();
        subcategory.setSubcategoryId(subcategoryId);
        subcategory.setSubCategoryName("Updated Subcategory");

        when(subCategoryService.updateSubcategory(subcategory, subcategoryId)).thenReturn(subcategory);

        ResponseEntity<SuccessResponse> responseEntity = subCategoryController.updateSubCategory(subcategory, subcategoryId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Subcategory Data Updated", responseEntity.getBody().getMessage());
        assertEquals(subcategory, responseEntity.getBody().getModuleData());
    }

    @Test
    void testUpdateSubCategory_IdNotFound() {
       
        long subcategoryId = 1L;
        Subcategory subcategory = new Subcategory();
        subcategory.setSubcategoryId(subcategoryId);
        subcategory.setSubCategoryName("Updated Subcategory");

        when(subCategoryService.updateSubcategory(subcategory, subcategoryId)).thenThrow(new RuntimeException("Id Not Found"));

        ResponseEntity<SuccessResponse> responseEntity = subCategoryController.updateSubCategory(subcategory, subcategoryId);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Id Not Found", responseEntity.getBody().getMessage());
    }

    @Test
    void testDeleteSubCategory_Success() {
     
        long subcategoryId = 1L;

        ResponseEntity<SuccessResponse> responseEntity = subCategoryController.deleteSubCategory(subcategoryId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Subcategory Data deleted", responseEntity.getBody().getMessage());
        assertNull(responseEntity.getBody().getModuleData());
    }

    @Test
    void testDeleteSubCategory_IdNotFound() {
 
        long subcategoryId = 1L;

        doThrow(new RuntimeException("Id Not Found")).when(subCategoryService).deleteSubcategory(subcategoryId);

        ResponseEntity<SuccessResponse> responseEntity = subCategoryController.deleteSubCategory(subcategoryId);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Id Not Found", responseEntity.getBody().getMessage());
    }
}

