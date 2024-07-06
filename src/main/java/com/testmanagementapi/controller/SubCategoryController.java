package com.testmanagementapi.controller;

import org.springframework.web.bind.annotation.RestController;
import com.testmanagementapi.entity.Subcategory;
import com.testmanagementapi.response.SuccessResponse;
import com.testmanagementapi.service.impl.SubCategoryServiceImpl;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@Validated
@RestController
@RequestMapping("/api/subcategory")
public class SubCategoryController {

  private SubCategoryServiceImpl subCategoryService;

  public SubCategoryController(SubCategoryServiceImpl subCategoryService) {
    this.subCategoryService = subCategoryService;
  }

  @PostMapping
  public ResponseEntity<SuccessResponse> createSubCategory(@Valid @RequestBody Subcategory subcategory) {

    Subcategory returnedSubcategory = subCategoryService.createSubcategory(subcategory);
    SuccessResponse successResponse = new SuccessResponse("SubCategory Created", 200, returnedSubcategory);
    return new ResponseEntity<>(successResponse, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<SuccessResponse> getAllSubCategory() {
    List<Subcategory> subcategoryList = subCategoryService.getAllSubCategory();
    SuccessResponse successResponse = new SuccessResponse("All Subcategory Data Retrieved", 200, subcategoryList);
    return new ResponseEntity<>(successResponse, HttpStatus.OK);
  }

  @GetMapping("/{SubCategory-id}")
  public ResponseEntity<SuccessResponse> getSubCategory(@PathVariable("SubCategory-id") long subCategoryId) {
    Optional<Subcategory> optionalSubcategory = subCategoryService.getSubCategory(subCategoryId);
    SuccessResponse successResponse = new SuccessResponse("Subcategory Data Retrieved", 200, optionalSubcategory);
    return new ResponseEntity<>(successResponse, HttpStatus.OK);
  }

  @PutMapping("/{SubCategory-id}")
  public ResponseEntity<SuccessResponse> updateSubCategory(@Valid @RequestBody Subcategory subcategory,
      @PathVariable("SubCategory-id") long subCategoryId) {

    Subcategory updatesSubcategory = subCategoryService.updateSubcategory(subcategory, subCategoryId);
    SuccessResponse successResponse = new SuccessResponse("Subcategory Data Updated", 200, updatesSubcategory);
    return new ResponseEntity<>(successResponse, HttpStatus.OK);
  }

  @DeleteMapping("/{SubCategory-id}")
  public ResponseEntity<SuccessResponse> deleteSubCategory(@PathVariable("SubCategory-id") long subCategoryId) {
    subCategoryService.deleteSubcategory(subCategoryId);
    SuccessResponse successResponse = new SuccessResponse("Subcategory Data deleted", 200, null);
    return new ResponseEntity<>(successResponse, HttpStatus.OK);
  }

}
