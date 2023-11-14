# Crypto Recommendation Service

The Crypto Recommendation Service is a Spring Boot application that provides recommendations for cryptocurrency investments based on historical price data. It exposes several API endpoints for retrieving crypto statistics.

## APIs

1. **Get Crypto Stats for Symbol:**
    - Endpoint: `/cryptos/{symbolName}/stats`
    - Method: `GET`
    - Description: Retrieve statistics for a specific cryptocurrency symbol.
    - Example: `/cryptos/BTC/stats`

2. **Get Normalized Stats Sorted:**
    - Endpoint: `/cryptos/stats/normalized-sorted`
    - Method: `GET`
    - Description: Retrieve a descending sorted list of all cryptocurrencies based on their normalized range (max-min)/min.

3. **Get Crypto with Highest Normalized Range for Date:**
    - Endpoint: `/cryptos/stats/highest-range/{date}`
    - Method: `GET`
    - Description: Retrieve the cryptocurrency with the highest normalized range for a specific date.
    - Date Format: `yyyy-MM-dd`
    - Example: `/cryptos/stats/highest-range/2023-01-01`

## Running the Application

### Prerequisites

- Java Development Kit (JDK) version 17 or higher
- Docker installed and running on your machine (if you want to run with docker)

### Build and Run Locally

1. Build the Spring Boot application:
   ```bash
   ./gradlew build
   ```

2. Run the application locally:
   ```bash
   java -jar build/libs/crypto-recommendation-service.jar
   ```

   The application will be accessible at [http://localhost:8080](http://localhost:8080).

3. Access Swagger Documentation:
   - Visit [http://localhost:8080](http://localhost:8080) for interactive API documentation using Swagger.

### Build and Run with Docker

1. Build the Docker image:
   ```bash
   ./gradlew buildDocker
   ```

2. Run the Docker container:
   ```bash
   docker run -p 8080:8080 -d <YOUR_DOCKER_IMAGE_NAME>:latest
   ```

   The application will be accessible at [http://localhost:8080](http://localhost:8080).

3. Access Swagger Documentation:
   - Visit [http://localhost:8080](http://localhost:8080) for interactive API documentation using Swagger.
