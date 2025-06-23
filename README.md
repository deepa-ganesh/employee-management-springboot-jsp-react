# Employee Management System

The employee management system with two different frontend implementations:
1. JSP with Apache Tiles
2. React

The employee management system built using Spring Boot, JSP with Apache Tiles, Spring Data JPA, and H2 in-memory database. The application provides a user-friendly interface for managing employee records with built-in validations and relationships.

## Features

### Employee Management
- **View Employees**: List all employees with their key details in a tabular format
- **Add/Edit Employee**: Manage employee records with comprehensive form validation
- **Delete Employee**: Remove employees with confirmation dialog
- **View Details**: Detailed view of individual employee information

### Field Validations
- **First Name (Required)**
  - Length: 1-50 characters
  - Proper validation messages

- **Last Name (Required)**
  - Length: 1-50 characters
  - Proper validation messages

- **Date of Birth (Required)**
  - Must be a past date
  - Date validation

- **Department (Required)**
  - Dropdown selection (HR, Engineering, Finance, Marketing)

- **Salary (Required)**
  - Must be a positive number
  - Supports multiple currencies (AED default, USD, EUR, GBP, INR, JPY)

- **Manager (Optional)**
  - Cannot select self as manager

### Technical Features
- Server-side validation using Spring's validation framework
- Responsive layout using Apache Tiles
- In-memory H2 database for easy testing
- RESTful API endpoints for data operations
- Proper error handling and user feedback

## Technology Stack

### Backend
- Java 17
- Spring Boot 2.7.18
- Spring MVC
- Spring Data JPA
- H2 Database
- Maven

### Frontend (JSP Version)
- JSP (JavaServer Pages)
- JSTL (JSP Standard Tag Library)
- Apache Tiles (Template Framework)
- CSS3 (Styling)

### Frontend (React Version)
- React 18
- React Router DOM
- Axios
- CSS3
- npm

## Project Structure

The project contains both JSP and React implementations in the same repository:

```
src/main/java/com/pcfc/assignment/
├── aop/            # AOP for traceability
├── common/         # Common utilities and enums
├── config/         # Configuration classes
├── controller/     # MVC & REST controllers
├── converter/      # DTO ↔ Entity converters
├── dto/            # Data Transfer Objects
├── entity/         # JPA entities
├── exception/      # Custom exceptions
├── repository/     # Data repositories
└── service/        # Business logic

src/main/webapp/
├── WEB-INF/
│   ├── views/      # JSP view templates
│   └── tiles/      # Tiles configuration
└── resources/      # Static resources

react-ui/           # React frontend implementation
├── src/
│   ├── components/ # React components
│   ├── services/   # API services
│   └── styles/     # CSS styles
└── public/         # Static files
```

## Running the Application

### Backend (Spring Boot)

1. Clone the repository:
   ```bash
   git clone https://github.com/deepa-ganesh/employee-management-springboot-jsp-react.git
   cd employee-management-springboot-jsp-react
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

4. Access the JSP version:
   - Open browser and navigate to: `http://localhost:8080/employees/list`
   - H2 Console: `http://localhost:8080/h2-console`
     - JDBC URL: `jdbc:h2:mem:testdb`
     - Username: `sa`
     - Password: (empty)

### Frontend (React)

1. Navigate to React UI directory:
   ```bash
   cd react-ui
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the React development server:
   ```bash
   npm start
   ```

4. Access the React version:
   - Open browser and navigate to: `http://localhost:3000`

## Using Different Versions

You can use either the JSP version or the React version:

- **JSP Version** (Traditional Server-Side Rendering)
  - Integrated with Spring Boot
  - Runs on port 8080
  - Server-side templating

- **React Version** (Modern Single-Page Application)
  - Separate frontend application
  - Runs on port 3000
  - REST API consumption
