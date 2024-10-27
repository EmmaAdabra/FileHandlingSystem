# Student Management System

## Overview
The **Student Management System** is a terminal-based Java application designed for managing student records. It provides functionalities for adding, updating, displaying, and deleting student information stored in CSV files. This project covers file handling concepts in Java and is part of my learning journey to understand Java file operations. It follows a layered architecture, adhering to SOLID principles, ensuring maintainability and scalability.

## Features
- **Student Record Management**: Add, update, delete, and display student records.
- **User Authentication**: Admin login and sign-up functionality for secure access.
- **Data Storage**: Student records are stored in a CSV file format for easy data manipulation and retrieval.

## Project Structure
```bash
studentManagementSystem/        # Project root directory
│ 
├── src/                        # Source code directory
│   ├── controller/             # Presentation Layer (handles user input/output)
│   │   └── AdminController.java # Class to manage admin-related operations
│   │
│   ├── services/               # Business Logic Layer
│   │   ├── StudentService.java  # Interface defining student management operations
│   │   ├── UserService.java     # Interface defining user management operations
│   │   ├── StudentServiceImpl.java # Class implementing StudentService
│   │   └── UserServiceImpl.java    # Class implementing UserService
│   │
│   ├── model/                  # Core project logic and entities
│   │   ├── Student.java         # Class representing a student entity
│   │   └── User.java            # Class representing an admin user entity
│   │
│   ├── repository/             # Data Access Layer
│   │   ├── StudentRepository.java # Interface defining methods for student data access
│   │   ├── UserRepository.java    # Interface defining methods for user data access
│   │   ├── StudentRepositoryImpl.java # Class implementing StudentRepository for CSV operations
│   │   └── UserRepositoryImpl.java    # Class implementing UserRepository for CSV operations
│   │
│   ├── util/                   # Utility classes
│   │   └── CsvUtils.java       # Helper class for CSV file operations
│   │
│   └── App.java                # Main application class to run the program
│
└── README.md                   # Project documentation
```
## Technologies Used
- **Java**: The primary programming language used for the application.
- **CSV File Format**: For data storage and retrieval.

## Getting Started

### Prerequisites
- Java Development Kit (JDK) installed on your machine.
- A text editor or IDE (e.g., IntelliJ IDEA, Eclipse) for development.

### Installation
1. Clone the repository:
    ```bash
    git clone https://github.com/engineerLiberty/FileHandlingSystem
       cd StudentManagementSystem
    ```
2. Compile the source code:
    ```bash
    javac src/**/*.java -d out
    ```
3. Compile the source code:
    ```bash
   java -cp out App
   ```
## Usage
- Start the application, and follow the prompts in the terminal to perform operations such as adding, updating, or deleting student records.
- Use the admin login functionality to access the system.