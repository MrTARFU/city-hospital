# Hospital Backend Application

## Description

This is a backend application for managing hospital consults and patient data. The system allows creating and managing consults, retrieving patient consults and symptoms, getting top specialties based on unique patients, and retrieving a list of patients with pagination, sorting, and filtering capabilities.

## Technologies Used

- Java 21
- Spring Boot 3.x
- Spring Data JPA
- Hibernate
- Liquibase
- PostgreSQL
- Docker
- Maven
- OpenAPI 3 (Swagger UI)

## Installation

Follow these steps to set up and run the application locally:

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/MrTARFU/city-hospital.git
   cd hospital-backend

## Run the Application 

Ensure Docker is installed and running on your system. Run the following command to start the application using Docker:

    docker-compose up --build

Install the dependencies using Maven:

    mvn clean install    

## Usage

### API Documentation

The application exposes the following endpoints:

#### 1. Create Consults
- **Endpoint**: `POST /api/consults`
- **Headers**:
  - `environment`: The environment in which the API is being called (e.g.,`local`, `dev`, `test`).
- **Request Body**: JSON with `doctor`, `patientId`, and `specialtyId`.
- **Example**:
    ```json
    {
      "Doctor": "António",
      "PatientId": 1,
      "SpecialtyId": 1
    }
    ```

#### 2. Get Patient Consults and Symptoms
- **Endpoint**: `GET /api/patients/{id}/consults`
- **Headers**:
    - `environment`: The environment in which the API is being called (e.g.,`local`, `dev`, `test`).
- **Path Parameter**: `id` (Patient ID)

#### 3. Get Top Specialties
- **Endpoint**: `GET /api/specialties/top`
- **Headers**:
    - `environment`: The environment in which the API is being called (e.g.,`local`, `dev`, `test`).
- **Query Parameter**: `minPatients` (minimum number of unique patients)

#### 4. Get All Patients
- **Endpoint**: `GET /api/patients`
- **Headers**:
    - `environment`: The environment in which the API is being called (e.g.,`local`, `dev`, `test`).
- **Query Parameters**: `page`, `size`, `sortBy`

### OpenAPI Documentation

The API documentation is available via Swagger UI. After starting the application, visit:

- **Swagger UI**: [http://localhost:8010/swagger-ui.html](http://localhost:8010/swagger-ui.html)
