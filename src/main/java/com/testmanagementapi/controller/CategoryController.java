package com.testmanagementapi.controller;

import org.springframework.web.bind.annotation.RestController;

import com.testmanagementapi.entity.Category;
import com.testmanagementapi.response.SuccessResponse;
import com.testmanagementapi.service.CategoryService;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private CategoryService categoryService;
    
    public CategoryController(CategoryService categoryService)
    {
        this.categoryService=categoryService;
    }

    @PostMapping
    public ResponseEntity<SuccessResponse> createCategory(@RequestBody Category category)
    {   
       try{
        Category returnedCategory = categoryService.createCategory(category);
        SuccessResponse successResponse = new SuccessResponse("New Category Is Created", 200, returnedCategory);
        return new ResponseEntity<>(successResponse,HttpStatus.OK);
       }
       catch(Exception exception)
       {
        SuccessResponse successResponse = new SuccessResponse("Duplicate Data", 400, null);
        return new ResponseEntity<>(successResponse,HttpStatus.BAD_REQUEST);
       }

    }

    @GetMapping
    public ResponseEntity<SuccessResponse> getAllCategory() {
        
        List<Category> categoryList = categoryService.getAllCategory();
        SuccessResponse successResponse = new SuccessResponse("All Categories Retrieved",200,categoryList);
        return new ResponseEntity<>(successResponse,HttpStatus.OK);
    }

    @GetMapping("/{Category_id}")
    public ResponseEntity<SuccessResponse> getCategory(@PathVariable("Category_id") long categoryId) {
        
       try{
        Optional<Category> optionalCategory = categoryService.getCategory(categoryId);
        SuccessResponse successResponse = new SuccessResponse("Category Retrieved By Specified Id",200,optionalCategory);
        return new ResponseEntity<>(successResponse,HttpStatus.OK);
       }
       catch(Exception exception)
       {
        SuccessResponse successResponse = new SuccessResponse("Id Not Found", 400, exception);
        return new ResponseEntity<>(successResponse,HttpStatus.BAD_REQUEST);
       }
    }
    
    @PutMapping("/{Category_id}")
    public ResponseEntity<SuccessResponse> putCategory(@PathVariable("Category_id") long categoryId, @RequestBody Category category) {
        
        try{
            Category updatedCategory = categoryService.updateCategory(category, categoryId);
            SuccessResponse successResponse = new SuccessResponse("Category Updated",200,updatedCategory);
            return new ResponseEntity<>(successResponse,HttpStatus.OK);
        }
        catch(Exception exception)
        {
         SuccessResponse successResponse = new SuccessResponse("Id Not Found", 400, exception);
         return new ResponseEntity<>(successResponse,HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{Category_id}")
    public ResponseEntity<SuccessResponse> deleteCategory(@PathVariable("Category_id") long categoryId)
    {
       try{
        categoryService.deleteCategory(categoryId);
        SuccessResponse successResponse = new SuccessResponse("Category Deleted By Specified Id",200,null);
        return new ResponseEntity<>(successResponse,HttpStatus.OK);
       }
       catch(Exception exception)
        {
         SuccessResponse successResponse = new SuccessResponse("Id Not Found", 400, exception);
         return new ResponseEntity<>(successResponse,HttpStatus.BAD_REQUEST);
        }
    }
}
