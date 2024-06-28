package com.testmanagement_api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.testmanagement_api.entity.Subcategory;
import com.testmanagement_api.reponsehandler.SuccessResponse;
import com.testmanagement_api.service.SubCategoryService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/subcategory")
public class SubCategoryController {

    @Autowired
    SubCategoryService subCategoryService;

    @PostMapping("/create")
    public ResponseEntity<SuccessResponse> createSubCategory(@RequestBody Subcategory subcategory) {
       try{
        Subcategory returnedSubcategory = subCategoryService.createSubcategory(subcategory);
        SuccessResponse successResponse = new SuccessResponse("SubCategory Created", 200, returnedSubcategory);
        return new ResponseEntity<>(successResponse,HttpStatus.OK);
       }
       catch(Exception ex)
       {
        SuccessResponse successResponse = new SuccessResponse("Duplicate Data", 400, ex);
        return new ResponseEntity<>(successResponse,HttpStatus.BAD_REQUEST);
       }
    }

    @GetMapping("/getall")
    public ResponseEntity<SuccessResponse> getAllSubCategory()
    {
            List<Subcategory> subcategoryList = subCategoryService.getAllSubCategory();
            SuccessResponse successResponse = new SuccessResponse("All Subcategory Data Retrived", 200, subcategoryList);
            return new ResponseEntity<>(successResponse,HttpStatus.OK);
    }

    @GetMapping("/getbyid/{subcat_id}")
    public ResponseEntity<SuccessResponse> getSubCategory(@PathVariable("subcat_id") long subcategory_id)
    {
      try{
        Optional<Subcategory> optionalSubcategory = subCategoryService.getSubCategory(subcategory_id);
        SuccessResponse successResponse = new SuccessResponse("Subcategory Data Retrived", 200, optionalSubcategory);
        return new ResponseEntity<>(successResponse,HttpStatus.OK);
      }
      catch(Exception ex)
       {
        SuccessResponse successResponse = new SuccessResponse("Id Not Found", 400, ex);
        return new ResponseEntity<>(successResponse,HttpStatus.BAD_REQUEST);
       }
    }

    @PutMapping("/update/{subcat_id}")
    public ResponseEntity<SuccessResponse> updateSubCategory(@RequestBody Subcategory subcategory , @PathVariable("subcat_id") long subcategory_id)
    {
        try{
        Subcategory updatesSubcategory = subCategoryService.updateSubcategory(subcategory, subcategory_id);
        SuccessResponse successResponse = new SuccessResponse("Subcategory Data Updated", 200, updatesSubcategory);
        return new ResponseEntity<>(successResponse,HttpStatus.OK);
      }
      catch(Exception ex)
       {
        SuccessResponse successResponse = new SuccessResponse("Id Not Found", 400, ex);
        return new ResponseEntity<>(successResponse,HttpStatus.BAD_REQUEST);
       }
    }

    @DeleteMapping("/delete/{subcat_id}")
    public ResponseEntity<SuccessResponse> deleteSubCategory(@PathVariable("subcat_id") long subcategory_id)
    {
        try{
            subCategoryService.deleteSubcategory(subcategory_id);
            SuccessResponse successResponse = new SuccessResponse("Subcategory Data deleted", 200, null);
            return new ResponseEntity<>(successResponse,HttpStatus.OK);
          }
          catch(Exception ex)
           {
            SuccessResponse successResponse = new SuccessResponse("Id Not Found", 400, ex);
            return new ResponseEntity<>(successResponse,HttpStatus.BAD_REQUEST);
           }
    }
    
}
