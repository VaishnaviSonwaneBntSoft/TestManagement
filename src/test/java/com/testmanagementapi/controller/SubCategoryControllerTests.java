package com.testmanagementapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.testmanagementapi.entity.Subcategory;
import com.testmanagementapi.response.SuccessResponse;
import com.testmanagementapi.service.impl.SubCategoryServiceImpl;

@SpringBootTest
class SubCategoryControllerTests {

    @Mock
    private SubCategoryServiceImpl subCategoryService;

    @InjectMocks
    private SubCategoryController subCategoryController;

    @Test
    void testCreateSubCategory() {
        Subcategory mockSubcategory = new Subcategory(); 
        when(subCategoryService.createSubcategory(any(Subcategory.class))).thenReturn(mockSubcategory);

        Subcategory requestSubcategory = new Subcategory(); 

        ResponseEntity<SuccessResponse> responseEntity = subCategoryController.createSubCategory(requestSubcategory);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("SubCategory Created", responseEntity.getBody().getMessage());
        
    }

    @Test
    void testGetAllSubCategory() {
        List<Subcategory> mockSubcategoryList = Arrays.asList(new Subcategory(), new Subcategory());
        when(subCategoryService.getAllSubCategory()).thenReturn(mockSubcategoryList);

        ResponseEntity<SuccessResponse> responseEntity = subCategoryController.getAllSubCategory();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("All Subcategory Data Retrieved", responseEntity.getBody().getMessage());
    
    }

    @Test
    void testGetSubCategory() {
        long subcategoryId = 1L;
        Subcategory mockSubcategory = new Subcategory(); 
        when(subCategoryService.getSubCategory(subcategoryId)).thenReturn(Optional.of(mockSubcategory));

        ResponseEntity<SuccessResponse> responseEntity = subCategoryController.getSubCategory(subcategoryId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Subcategory Data Retrieved", responseEntity.getBody().getMessage());
 
    }

    @Test
    void testUpdateSubCategory() {
        long subcategoryId = 1L;
        Subcategory mockSubcategory = new Subcategory(); 
        when(subCategoryService.updateSubcategory(any(Subcategory.class), eq(subcategoryId))).thenReturn(mockSubcategory);

        Subcategory requestSubcategory = new Subcategory(); 

        ResponseEntity<SuccessResponse> responseEntity = subCategoryController.updateSubCategory(requestSubcategory, subcategoryId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Subcategory Data Updated", responseEntity.getBody().getMessage());
        
    }

    @Test
    void testDeleteSubCategory() {
        long subcategoryId = 1L;

        ResponseEntity<SuccessResponse> responseEntity = subCategoryController.deleteSubCategory(subcategoryId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Subcategory Data deleted", responseEntity.getBody().getMessage());
        
    }
}


