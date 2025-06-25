# FLEXBETS-SPORT

## Pull request deployment flow
PR **any** to **development** => dev \
PR **development** to **stage** => stage \
PR **stage** to **main** => prd

## Overview
FLEXBETS-SPORT is a service responsible for REST communication with sports APIs and live data services. It collects and transforms data into a unified response, which is made available via REST controllers. The project is built using Java and Spring Boot.

## Features
- REST API for sports data.
- Integration with live data services.
- Data transformation and unification.
- OpenAPI documentation for API endpoints.

## Prerequisites
- Java 21 or higher.
- Docker and Docker Compose.
- PostgreSQL database.

## Setup Instructions

### Clone the Repository
```bash
git clone <repository-url>
cd flexbets-sport
```

### Configure Environment Variables
Copy the `.env.example` file to `.env` and update the values as needed:
```bash
cp .env.example .env
```

### Build the Project
Use Gradle to build the project:
```bash
./gradlew build
```

### Run with Docker Compose
Start the application and its dependencies using Docker Compose:
```bash
docker-compose up --build
```

### Access the Application
- API: [http://localhost:8080](http://localhost:8080)
- PostgreSQL: `localhost:5432`

## Development
### Running Locally
You can run the application locally using:
```bash
./gradlew bootRun
```

### Running Tests
Execute the test suite using:
```bash
./gradlew test
```

## API Documentation
The API documentation is available at:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## License
This project is licensed under the Apache License 2.0. See the LICENSE file for details.
