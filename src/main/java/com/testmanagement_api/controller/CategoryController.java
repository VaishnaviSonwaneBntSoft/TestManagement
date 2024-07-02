package com.testmanagement_api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.testmanagement_api.entity.Category;
import com.testmanagement_api.reponsehandler.SuccessResponse;
import com.testmanagement_api.service.CategoryService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<SuccessResponse> createCategory(@RequestBody Category category)
    {   
       try{
        Category returnedcategroy = categoryService.createCategory(category);
        SuccessResponse successResponse = new SuccessResponse("New Category Is Created", 200, returnedcategroy);
        return new ResponseEntity<SuccessResponse>(successResponse,HttpStatus.OK);
       }
       catch(Exception exception)
       {
        SuccessResponse successResponse = new SuccessResponse("Duplicate Data", 400, exception);
        return new ResponseEntity<SuccessResponse>(successResponse,HttpStatus.BAD_REQUEST);
       }

    }

    @GetMapping("/getall")
    public ResponseEntity<SuccessResponse> getAllCategory() {
        
        List<Category> catgoryList = categoryService.getAllCategory();
        SuccessResponse successResponse = new SuccessResponse("All Categories Retrives",200,catgoryList);
        return new ResponseEntity<SuccessResponse>(successResponse,HttpStatus.OK);
    }

    @GetMapping("/getbyid/{category_id}")
    public ResponseEntity<SuccessResponse> getCategory(@PathVariable("category_id") long category_id) {
        
       try{
        Optional<Category> optionalCategory = categoryService.getCategory(category_id);
        SuccessResponse successResponse = new SuccessResponse("Category Retrived By Specified Id",200,optionalCategory);
        return new ResponseEntity<SuccessResponse>(successResponse,HttpStatus.OK);
       }
       catch(Exception exception)
       {
        SuccessResponse successResponse = new SuccessResponse("Id Not Found", 400, exception);
        return new ResponseEntity<SuccessResponse>(successResponse,HttpStatus.BAD_REQUEST);
       }
    }
    
    @PutMapping("/update/{category_id}")
    public ResponseEntity<SuccessResponse> putCategory(@PathVariable("category_id") long category_id, @RequestBody Category category) {
        
        try{
            Category updatedCategory = categoryService.updateCategory(category, category_id);
            SuccessResponse successResponse = new SuccessResponse("Category Updated",200,updatedCategory);
            return new ResponseEntity<SuccessResponse>(successResponse,HttpStatus.OK);
        }
        catch(Exception exception)
        {
         SuccessResponse successResponse = new SuccessResponse("Id Not Found", 400, exception);
         return new ResponseEntity<SuccessResponse>(successResponse,HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{category_id}")
    public ResponseEntity<SuccessResponse> deleteCategory(@PathVariable("category_id") long category_id)
    {
       try{
        categoryService.deleteCategory(category_id);
        SuccessResponse successResponse = new SuccessResponse("Category Deleted By Specified Id",200,null);
        return new ResponseEntity<SuccessResponse>(successResponse,HttpStatus.OK);
       }
       catch(Exception exception)
        {
         SuccessResponse successResponse = new SuccessResponse("Id Not Found", 400, exception);
         return new ResponseEntity<SuccessResponse>(successResponse,HttpStatus.BAD_REQUEST);
        }
    }
}
