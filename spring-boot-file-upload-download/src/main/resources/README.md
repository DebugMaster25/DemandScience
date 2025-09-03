# Spring Boot File Upload and Download Application

This project is a Spring Boot application that implements file upload and download functionalities with email validation. Below are the instructions on how to set up and run the application.

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher

## Setup Instructions

1. **Clone the Repository**
   Clone the repository to your local machine using the following command:
   ```
   git clone <repository-url>
   ```

2. **Navigate to the Project Directory**
   Change your working directory to the project folder:
   ```
   cd spring-boot-file-upload-download
   ```

3. **Build the Project**
   Use Maven to build the project:
   ```
   mvn clean install
   ```

4. **Run the Application**
   You can run the application using the following command:
   ```
   mvn spring-boot:run
   ```

5. **Access the Endpoints**
   - **Upload File**: Send a POST request to `/upload` with the file and email in the form data.
   - **Download File**: Send a GET request to `/download/{filename}` to retrieve the file.

## Email Validation

The application includes an email validation feature that checks if the provided email address is in a valid format before processing the file upload.

## Testing

Unit tests are included in the project to ensure the functionality of the file upload and download features. You can run the tests using:
```
mvn test
```

## Dependencies

The project uses the following dependencies:
- Spring Boot Starter Web
- Spring Boot Starter Test

## License

This project is licensed under the MIT License.