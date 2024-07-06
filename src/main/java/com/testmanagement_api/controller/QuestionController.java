package com.testmanagement_api.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import com.testmanagement_api.entity.QuestionModel;
import com.testmanagement_api.exceptionhandler.DuplicateEntries;
import com.testmanagement_api.reponsehandler.SuccessResponse;
import com.testmanagement_api.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@Slf4j
@RestController
@RequestMapping("/api/test")
public class QuestionController {

    private QuestionService questionService; 

    public QuestionController(QuestionService questionService)
    {
        this.questionService=questionService;
    }

    @PostMapping
    public ResponseEntity<SuccessResponse> createMcqQuestion(@RequestBody QuestionModel model)
    {
        log.info("Received request to create a new MCQ question: {}", model);
          try{
            questionService.createMcqQuestion(model);
            SuccessResponse successResponse = new SuccessResponse("New Question Saved" , 201 , model);
            log.info("Successfully created new MCQ question: {}", model);
            return new ResponseEntity<>(successResponse , HttpStatus.CREATED);
          }
          catch(Exception exception)
          {
            log.error( exception.getMessage(), exception);
            SuccessResponse successResponse = new SuccessResponse(exception.getMessage(), 400,exception);
            return new ResponseEntity<>(successResponse, HttpStatus.BAD_REQUEST);
          }
       
    }

    @GetMapping
    public ResponseEntity<SuccessResponse> getAllQuestionsData()
    {
        try{
          List<QuestionModel> dataList = questionService.getAllQuestionsData();
          SuccessResponse successResponse = new SuccessResponse("All Data Retrieved" , 200 , dataList);
          return new ResponseEntity<>(successResponse , HttpStatus.OK);
        }
        catch(Exception exception)
        {
          log.error(exception.getMessage(), exception);
         SuccessResponse successResponse = new SuccessResponse(exception.getMessage(), 400,null);
         return new ResponseEntity<>(successResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> updateQuestionData(@RequestBody QuestionModel model , @PathVariable("id") long questionId)
    { 
        try{
          QuestionModel model2 = questionService.updateQuestionData(model, questionId);
          SuccessResponse sResponse = new SuccessResponse("Question Data Updated", 200, model2);
          return new ResponseEntity<>(sResponse, HttpStatus.OK);
        }
        catch(Exception exception)
       {
        log.error(exception.getMessage(), exception);
        SuccessResponse successResponse = new SuccessResponse(exception.getMessage(), 400,null);
        return new ResponseEntity<>(successResponse, HttpStatus.BAD_REQUEST);
       }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> getQuestionDataById(@PathVariable("id") long questionId)
    {
        try{
          Optional<QuestionModel> model = questionService.getQuestionDataById(questionId);
          SuccessResponse successResponse = new SuccessResponse("Data Retrieved by Id",200,model);
          return new ResponseEntity<>(successResponse, HttpStatus.OK);
        }
        catch(Exception exception)
       {
        log.error(exception.getMessage(), exception);
        SuccessResponse successResponse = new SuccessResponse(exception.getMessage(), 400,null);
        return new ResponseEntity<>(successResponse, HttpStatus.BAD_REQUEST);
       }
      
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteQuestion(@PathVariable("id") long questionId)
    {   
       try{
        questionService.deleteQuestionDataById(questionId);
        SuccessResponse successResponse = new SuccessResponse("Question Data Deleted", 204,null);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
       }catch(Exception exception)
       {
        log.error(exception.getMessage(), exception);
        SuccessResponse successResponse = new SuccessResponse(exception.getMessage(), 400,null);
        return new ResponseEntity<>(successResponse, HttpStatus.BAD_REQUEST);
       }
    }

    @PostMapping("/upload")
   public ResponseEntity<SuccessResponse> uploadBulkQuestions(@RequestParam("file") MultipartFile file)
   {
      try{
        questionService.uploadBulkQuestions(file);
        SuccessResponse successResponse = new SuccessResponse("Question Data Uploaded", 200,null);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
      }
      catch(DuplicateEntries exception)
      {
        log.error(exception.getMessage(), exception);
        SuccessResponse successResponse = new SuccessResponse("Duplicate Questions Found In Excel Sheet", 400,exception.queList);
        return new ResponseEntity<>(successResponse, HttpStatus.BAD_REQUEST);
      }
      catch(Exception exception)
      {
        log.error(exception.getMessage(), exception);
        SuccessResponse successResponse = new SuccessResponse(exception.getMessage(), 400,null);
        return new ResponseEntity<>(successResponse, HttpStatus.BAD_REQUEST);
      }
   }
    
}
