package com.testmanagement_api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    QuestionService tService;

    @PostMapping("/create")
    public ResponseEntity<SuccessResponse> CreateMcqQuestion(@RequestBody QuestionModel model)
    {
        log.info("Received request to create a new MCQ question: {}", model);
          try{
            tService.CreateMcqQuestion(model);
            SuccessResponse successResponse = new SuccessResponse("New Question Saved" , 201 , model);
            log.info("Successfully created new MCQ question: {}", model);
            return new ResponseEntity<SuccessResponse>(successResponse , HttpStatus.CREATED);
          }
          catch(Exception exception)
          {
            log.error( exception.getMessage(), exception);
            SuccessResponse succsesResponse = new SuccessResponse(exception.getMessage(), 400,exception);
            return new ResponseEntity<SuccessResponse>(succsesResponse, HttpStatus.BAD_REQUEST);
          }
       
    }

    @GetMapping("/getdata")
    public ResponseEntity<SuccessResponse> GetAllQuestionsData()
    {
        try{
          List<QuestionModel> DataList = tService.GetAllQuestionsData();
          SuccessResponse successResponse = new SuccessResponse("All Data Retrived" , 200 , DataList);
          return new ResponseEntity<SuccessResponse>(successResponse , HttpStatus.OK);
        }
        catch(Exception exception)
        {
          log.error(exception.getMessage(), exception);
         SuccessResponse succsesResponse = new SuccessResponse(exception.getMessage(), 400,null);
         return new ResponseEntity<SuccessResponse>(succsesResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SuccessResponse> updateQuestionData(@RequestBody QuestionModel model , @PathVariable("id") long Question_id)
    { 
        try{
          QuestionModel model2 = tService.updateQuestionData(model, Question_id);
           SuccessResponse sResponse = new SuccessResponse("Question Data Updated", 200, model2);
            return new ResponseEntity<SuccessResponse>(sResponse, HttpStatus.OK);
        }
        catch(Exception exception)
       {
        log.error(exception.getMessage(), exception);
        SuccessResponse succsesResponse = new SuccessResponse(exception.getMessage(), 400,null);
        return new ResponseEntity<SuccessResponse>(succsesResponse, HttpStatus.BAD_REQUEST);
       }
    }

    @GetMapping("/getbyId/{id}")
    public ResponseEntity<SuccessResponse> getQuestionDataById(@PathVariable("id") long Question_id)
    {
        try{
          Optional<QuestionModel> model = tService.getQuestionDataById(Question_id);
          SuccessResponse succsesResponse = new SuccessResponse("Data Retrived by Id",200,model);
          return new ResponseEntity<SuccessResponse>(succsesResponse, HttpStatus.OK);
        }
        catch(Exception exception)
       {
        log.error(exception.getMessage(), exception);
        SuccessResponse succsesResponse = new SuccessResponse(exception.getMessage(), 400,null);
        return new ResponseEntity<SuccessResponse>(succsesResponse, HttpStatus.BAD_REQUEST);
       }
      
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<SuccessResponse> deleteQuestion(@PathVariable("id") long Question_id)
    {   
       try{
        tService.deleteQuestionDataById(Question_id);
        SuccessResponse succsesResponse = new SuccessResponse("Question Data Deleted", 204,null);
        return new ResponseEntity<SuccessResponse>(succsesResponse, HttpStatus.OK);
       }catch(Exception exception)
       {
        log.error(exception.getMessage(), exception);
        SuccessResponse succsesResponse = new SuccessResponse(exception.getMessage(), 400,null);
        return new ResponseEntity<SuccessResponse>(succsesResponse, HttpStatus.BAD_REQUEST);
       }
    }

    @PostMapping("/upload")
   public ResponseEntity<SuccessResponse> uploadBulkQuestions(@RequestParam("file") MultipartFile file)
   {
      try{
        tService.uploadBulkQuestions(file);
        SuccessResponse succsesResponse = new SuccessResponse("Question Data Uploaded", 200,null);
        return new ResponseEntity<SuccessResponse>(succsesResponse, HttpStatus.OK);
      }
      catch(DuplicateEntries exception)
      {
        log.error(exception.getMessage(), exception);
        SuccessResponse succsesResponse = new SuccessResponse("Duplicate Questions Found In Excel Sheet", 400,exception.queList);
        return new ResponseEntity<SuccessResponse>(succsesResponse, HttpStatus.BAD_REQUEST);
      }
      catch(Exception exception)
      {
        log.error(exception.getMessage(), exception);
        SuccessResponse succsesResponse = new SuccessResponse(exception.getMessage(), 400,null);
        return new ResponseEntity<SuccessResponse>(succsesResponse, HttpStatus.BAD_REQUEST);
      }
   }
    
}
