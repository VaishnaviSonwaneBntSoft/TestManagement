# TestManagement

This project implements an API for managing multiple-choice questions (MCQs) using Spring Boot.

## Features

- **CRUD API's for managing CREATE, UPDATE, DELETE, READ Operations**
- **Bulk upload of questions from an Excel file**
- **Error handling for duplicate entries and other exceptions**

## Technologies Used

- Java 21
- Spring Boot
- Spring Data JPA
- Lombok
- SLF4J (for logging)

## Prerequisites

- Java Development Kit (JDK) - Version 21
- Gradle Build Tool - Version 7.x
- PostgreSQL Database - Version 16
- IDE with Gradle support (Visual Studio Code)
- Internet access to download dependencies from Maven Central
  
## Getting Started

To run this project locally, follow these steps:

1. **Clone the repository:**
 
   To clone the repository, open your terminal and run:
   ```bash
   git clone https://github.com/VaishnaviSonwaneBntSoft/TestManagement.git 
   ```

2. **Change to project directory**
   ```bash
   cd TestManagement
   ```
   
3. **Set up the database:**
   
   Ensure you have PostgreSQL Version 8 installed locally or configure your database settings in application.properties.

4. **Build and run the application:**
    ```bash
    gradle build
    gradle bootRun
    ```

4. **Access the API endpoints:**

  Once the application is running, you can access the API using tools like Postman.

## API Endpoints

### CategoryController

Handles CRUD operations for managing categories.

- **POST**
  Create a new category.
  -*url:*
  ```bash
  http://localhost:8080/api/category
  ```
  -*body*
  ```bash
  {
    "categoryName": "Java",
    "categoryDesciption": "Core Java category"
  }
  ```
- **GET**
  Retrieve all categories.
   -*url:*
  ```bash
  http://localhost:8080/api/category
  ```
- **GET**
  Retrieve a category by ID.
   -*url:*
  ```bash
  http://localhost:8080/api/category/1
  ```
- **PUT**
  Update a category.
   -*url:*
  ```bash
  http://localhost:8080/api/category/1
  ```
   -*body*
  ```bash
  {
    "categoryId": 1,
    "categoryName": "Java",
    "categoryDesciption": "Core Java category"
  }
  ```
- **DELETE**
   Delete a category.
  -*url:*
  ```bash
  http://localhost:8080/api/category/1
  ```

### QuestionController

Manages operations related to questions.

- **POST**
  Create a new Question.
  -*url:*
  ```bash
  http://localhost:8080/api/question
  ```
  -*body*

```bash
{
    "subcategory": {
        "subcategoryId": "5",
        "category": {
            "categoryId": "",
            "categoryName": "java",
            "categoryDesciption": "categoryDesciption"
        },
        "subCategoryName": "Collection",
        "subCategoryDesciption": "Collections from Java"
    },
    "question": "In Spring Boot @RestController annotation is equivalent to",
    "option_one": "@Controller and @PostMapping",
    "option_two": "@Controller and @Component",
    "option_three": "@Controller and @ResponseBody",
    "option_four": "@Controller and @ResponseStatus",
    "correct_option": "@Controller and @ResponseBody",
    "positive_mark": "3",
    "negative_mark": "-1"
}
  ```
- **GET**
  Retrieve all Questions.
   -*url:*
  ```bash
  http://localhost:8080/api/question
  ```
- **GET**
  Retrieve a question by ID.
   -*url:*
  ```bash
  http://localhost:8080/api/question/1
  ```
- **PUT**
  Update a Question.
   -*url:*
  ```bash
  http://localhost:8080/api/question/1
  ```
   -*body*
 ```
{
    "question_id": "21"
		"subcategory": {
        "subcategoryId": "5",
        "category": {
            "categoryId": "",
            "categoryName": "java",
            "categoryDesciption": "categoryDesciption"
        },
        "subCategoryName": "Collection",
        "subCategoryDesciption": "Collections from Java"
    },
    "question": "In Spring Boot @RestController annotation is equivalent to",
    "option_one": "@Controller and @PostMapping",
    "option_two": "@Controller and @Component",
    "option_three": "@Controller and @ResponseBody",
    "option_four": "@Controller and @ResponseStatus",
    "correct_option": "@Controller and @ResponseBody",
    "positive_mark": "3",
    "negative_mark": "-1"
}
  ```
- **DELETE**
   Delete a Question.
  -*url:*
  ```bash
  http://localhost:8080/api/question/1
  ```
- **POST**
  Post bulk data
  -*url:*
  ```bash
  http://localhost:8080/api/question/uploadbulk
  ```
  Add excel file in form-data of body and give key name same as argument pass by api
### SubCategoryController

Handles CRUD operations for managing subcategories.

- **POST**
  Create a new Subcategory.
  -*url:*
  ```bash
  http://localhost:8080/api/subcategory
  ```
  -*body*
```bash
 {
    "category": {
        "categoryId": "",
        "categoryName": "java",
        "categoryDesciption": "categoryDesciption"
    },
    "subCategoryName": "Collection",
    "subCategoryDesciption": "Collections from Java"
}
  ```
- **GET**
  Retrieve all Subcategories.
   -*url:*
  ```bash
  http://localhost:8080/api/subcategory
  ```
- **GET**
  Retrieve a subcategory by ID.
   -*url:*
  ```bash
  http://localhost:8080/api/subcategory/1
  ```
- **PUT**
  Update a subcategory.
   -*url:*
  ```bash
  http://localhost:8080/api/subcategory/1
  ```
   -*body*
 ```bash
  {
    "subcategoryId": "5",
    "category": {
        "categoryId": "",
        "categoryName": "java",
        "categoryDesciption": "categoryDesciption"
    },
    "subCategoryName": "Collection",
    "subCategoryDesciption": "Collections from Java"
}
  ```
- **DELETE**
   Delete a subcategory.
  -*url:*
  ```bash
  http://localhost:8080/api/subcategory/1
  ```

- **POST**
  Upload bulk data.
  -*url:*
  ```bash
  http://localhost:8080/api/subcategory/uploadbulk
  ```
  Add file in value section of form-data present in body section of postman, give key name same as argument of api.
  

##  Error Handling

If a requested resource is not found or if there's a duplication error, appropriate error responses are returned with details.

## Logging

SLF4J is used for logging. Logs are available to track requests, errors, and other important information.

