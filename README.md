# TestManagement

This project implements an API for managing multiple-choice questions (MCQs) using Spring Boot.

## Features

- **Create MCQ Question:** Endpoint to create a new MCQ question.
- **Get All Questions:** Endpoint to fetch all existing MCQ questions.
- **Update Question:** Endpoint to update an existing MCQ question by ID.
- **Get Question by ID:** Endpoint to fetch a specific MCQ question by its ID.
- **Delete Question:** Endpoint to delete an existing MCQ question by ID.

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- Lombok
- SLF4J (for logging)

## Getting Started

To run this project locally, follow these steps:

1. **Clone the repository:**

   ```bash
   git clone https://github.com/your_username/testmanagement-api.git
   cd testmanagement-api
   
2. **Set up the database:**
   
   Ensure you have MySQL installed locally or configure your database settings in application.properties.

3. **Build and run the application:**
    ```bash
   ./mvnw spring-boot:run

4. **Access the API endpoints:**

  Once the application is running, you can access the API using tools like Postman.

## API Endpoints

1. **Create MCQ Question:**

   ```bash
   POST /api/test/create
   Request Body:
   ```bash
   {
  "question_id": 1,
  "question_text": "What is the capital of France?",
  "options": ["Paris", "London", "Berlin"],
  "correct_answer": "Paris"
  }


2. **Get All Questions:**

   ```bash
   GET /api/test/getdata

3. **Update Question:**

   ```bash
   PUT /api/test/update/{id}
   Request Body:
   ```bash
   {
  "question_text": "What is the capital of Germany?",
  "options": ["Paris", "London", "Berlin"],
  "correct_answer": "Berlin"
  }


4. **Get Question by ID:**

     ```bash
     GET /api/test/getbyId/{id}
     
5. **Delete Question:**

   ```bash
   DELETE /api/test/delete/{id}

##  Error Handling

If a requested resource is not found or if there's a duplication error, appropriate error responses are returned with details.

## Logging

SLF4J is used for logging. Logs are available to track requests, errors, and other important information.


### Instructions for Updating:

- **Project Name and Description:** Replace `Test Management API` with your actual project name and update the description as needed.
- **Features:** List all the main features of your API.
- **Technologies Used:** Update with the specific technologies and frameworks used in your project.
- **Getting Started:** Provide clear steps for cloning, setting up the database, building, and running the application.
- **API Endpoints:** Include examples of how to use each endpoint along with request body examples where applicable.
- **Error Handling:** Describe how errors are handled in your API.
- **Logging:** Mention the logging framework used and its purpose in your project.
- **Contributing:** Guidelines for contributing to the project.
- **License:** Specify the license under which your project is distributed.

Replace placeholders like `{id}`, `{your_username}`, and update paths (`/api/test/`) as per your actual API endpoints and project structure. This template should give a good starting point for creating a comprehensive README file for your GitHub repository.
