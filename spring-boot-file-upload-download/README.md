# Spring Boot File Upload and Download Application

This project is a Spring Boot application that provides endpoints for uploading and downloading files, along with email validation functionality.

## Features

- **File Upload**: Users can upload files to the server.
- **File Download**: Users can download files from the server.
- **Email Validation**: Validates email addresses using regex before processing file uploads.

## Project Structure

```
spring-boot-file-upload-download
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── fileapp
│   │   │               ├── FileAppApplication.java
│   │   │               ├── controller
│   │   │               │   └── FileController.java
│   │   │               ├── service
│   │   │               │   └── FileService.java
│   │   │               └── util
│   │   │                   └── EmailValidator.java
│   │   └── resources
│   │       ├── application.properties
│   │       └── README.md
│   └── test
│       └── java
│           └── com
│               └── example
│                   └── fileapp
│                       └── FileControllerTest.java
├── pom.xml
└── README.md
```

## Setup Instructions

1. **Clone the Repository**: 
   ```
   git clone <repository-url>
   cd spring-boot-file-upload-download
   ```

2. **Build the Project**: 
   ```
   mvn clean install
   ```

3. **Run the Application**: 
   ```
   mvn spring-boot:run
   ```

4. **Access the Endpoints**:
   - Upload File: `POST /upload`
   - Download File: `GET /download/{filename}`

## Usage Guidelines

- Ensure that the email address is valid before uploading files.
- Files will be stored in the specified directory as configured in `application.properties`.

## Dependencies

This project uses the following dependencies:
- Spring Boot Starter Web
- Spring Boot Starter Test

## License

This project is licensed under the MIT License.