# TestManagement

This project implements an API for managing multiple-choice questions (MCQs) using Spring Boot.

## Features

- **CRUD API's for managing CREATE, UPDATE, DELETE, READ Operations**
- **Bulk upload of questions from an Excel file**
- **Error handling for duplicate entries and other exceptions**

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- Lombok
- SLF4J (for logging)

## Prerequisites

- Java Development Kit (JDK) - Version 21
- Gradle Build Tool - Version 7.x
- PostgreSQL Database
- IDE with Gradle support (Visual Studio Code)
- Internet access to download dependencies from Maven Central
  
## Getting Started

To run this project locally, follow these steps:

1. **Clone the repository:**

   ```bash
   git clone https://github.com/VaishnaviSonwaneBntSoft/testmanagement-api.git
   cd testmanagement-api
   
2. **Set up the database:**
   
   Ensure you have PostgreSQL installed locally or configure your database settings in application.properties.

3. **Build and run the application:**
    ```bash
   ./mvnw spring-boot:run

4. **Access the API endpoints:**

  Once the application is running, you can access the API using tools like Postman.

## API Endpoints

### CategoryController

Handles CRUD operations for managing categories.

- **POST /api/category/create**: Create a new category.
- **GET /api/category/getall**: Retrieve all categories.
- **GET /api/category/getbyid/{category_id}**: Retrieve a category by ID.
- **PUT /api/category/update/{category_id}**: Update a category.
- **DELETE /api/category/delete/{category_id}**: Delete a category.

### QuestionController

Manages operations related to questions.

- **POST /api/test/create**: Create a new question.
- **GET /api/test/getdata**: Retrieve all questions.
- **GET /api/test/getbyId/{id}**: Retrieve a question by ID.
- **PUT /api/test/update/{id}**: Update a question.
- **DELETE /api/test/delete/{id}**: Delete a question.
- **POST /api/test/upload**: Uploads bulk data.

### SubCategoryController

Handles CRUD operations for managing subcategories.

- **POST /api/subcategory/create**: Create a new subcategory.
- **GET /api/subcategory/getall**: Retrieve all subcategories.
- **GET /api/subcategory/getbyid/{subcat_id}**: Retrieve a subcategory by ID.
- **PUT /api/subcategory/update/{subcat_id}**: Update a subcategory.

##  Error Handling

If a requested resource is not found or if there's a duplication error, appropriate error responses are returned with details.

## Logging

SLF4J is used for logging. Logs are available to track requests, errors, and other important information.

