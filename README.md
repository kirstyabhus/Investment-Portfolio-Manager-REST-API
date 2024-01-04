# Investment Portfolio Manager API 
## Description
Welcome to the Investment Portfolio Manager API, a robust backend system powered by Spring Boot. This API serves as a comprehensive solution for efficiently managing diverse investment portfolios comprising stocks and Exchange-Traded Funds (ETFs). Seamlessly handling multiple portfolios, this platform allows users to create, update, and oversee various investment portfolios, ensuring optimal control and management of investment assets. Additionally, the API introduces sustainability ratings, providing insights into the environmental, social, and governance (ESG) performance of individual investments. By leveraging sustainability ratings, users can assess the sustainable impact of their investment decisions, facilitating informed and responsible portfolio management.

## Table of Contents

1. [Key Features](#key-features)
2. [Documentation](#documentation)
3. [Technology Used](#technology-used)
4. [Prerequisites](#prerequisites)
5. [Installation](#installation)
6. [Running the Application](#running-the-application)
7. [Testing](#testing)

## Key Features:
- **Portfolio Operations**:
    - Create, read, update, delete, and sort portfolios effortlessly.
    - Seamlessly transfer investments between different portfolios for flexible asset management.
- **Investment Operations:**
    - Perform create, read, update, delete, and sort actions on investments.
    - Utilise advanced filtering options (by type) for precise data retrieval.
- **Automated Calculations:**
    - Automated computation of investment total values upon insertion into portfolios for streamlined data handling.
    - Dynamic recalculation of portfolio total values whenever investments are added or removed, ensuring real-time accuracy without manual intervention.
- **ESG Ratings Integration:**
    - Users' stocks and ETFs are automatically assigned an ESG rating through data sourced from MSCI, providing insights into the environmental, social, and governance (ESG) performance of individual investments.
#### Description of MSCI ESG Research
*"MSCI ESG Research provides in-depth research, ratings and analysis of the environmental, social and governance-related business practices of thousands of companies worldwide. Our research is designed to provide critical insights that can help institutional investors identify risks and opportunities that traditional investment research may overlook. The MSCI ESG Ratings are also used in the construction of the MSCI ESG Indexes, produced by MSCI, Inc." For more information, [click here](https://www.msci.com/our-solutions/esg-investing).*

## Documentation
The API documentation, powered by SpringDoc and Swagger, provides comprehensive insights into the functionalities of this application. Access the documentation at http://localhost:8080/swagger-ui/index.html while the application is running. Please note that accessing the documentation with this link requires the application to be active on port 8080. However, this can be changed with the addition of the line `server.port="your desired port number"` in the application.properties file.

The Swagger API documentation serves as a detailed guide showcasing available endpoints, their usage, request parameters, response formats, and error codes. It offers a user-friendly interface to explore and understand the API functionalities efficiently.

Ensure the application is running on port 8080 to fully leverage the rich documentation provided by Swagger, facilitating a smoother understanding and utilization of the API's capabilities.

## Endpoints
![Swagger Documentation of Endpoints](/assets/SwaggerDoc.PNG)
Access more details through the Swagger UI after running the application.

Example portfolio response (for get portfolio by id):
```json
{
        "portfolioId": "0ed506a6-ac9c-4f3e-b858-b50e9e8d29e1",
        "name": "Tech Investments",
        "totalValue": 5532.0,
        "investments": [
            {
                "type": "ETF",
                "id": "f5c03b4d-a6c7-4362-975f-c18ecfcf697c",
                "symbol": "VGT",
                "name": "Vanguard Information Technology ETF",
                "esgRating": "AA",
                "sharesQuantity": 15,
                "purchasePrice": 54.8,
                "totalValue": 822.0,
                "currentValue": 56.2
            },
            {
                "type": "Stock",
                "id": "00a78266-7492-4c5a-a605-83b6de4ec9cb",
                "symbol": "NVDA",
                "name": "NVIDIA Corp",
                "esgRating": "AAA",
                "sharesQuantity": 200,
                "purchasePrice": 23.25,
                "totalValue": 4650.0,
                "currentValue": 25.5
            }
        ]
    }
```
## Project Structure
- `controllers` : Contains classes responsible for handling incoming HTTP requests, interacting with services, processing data, and forming appropriate HTTP responses. 
- `services` : holds classes that encapsulate business logic or application-specific functionality. They perform operations requested by controllers, interact with repositories for data access, perform computations, and implement the core logic of the application.
- `repositories` :  Contains classes responsible for data access. They interact with the underlying data storage (JSON file). CRUD (Create, Read, Update, Delete) operations are performed here and an abstraction layer is provided for data access.
- `model` : Contains classes that represent the data entities. These classes define the structure of the data used within the application.
- `utility` : Contains functions for handling JSON data (reading and writing).
- `exceptions` : Used to hold custom exception classes or exception-related functionalities

### Data flow
`Users API request` > `Controller` > `Service` > `Repository` > `Utility` > `Repository` > `Service` > `Controller` > `User API response`

## Technology Used
- Java and Spring Boot: Used to create the application
- RESTful API: To handle the CRUD operations on the portfolios and investments
- JSON: Used to store portfolio data
- HashMap/List: JSON data was retrieved into a HashMap to store portfolios/investments and in Lists in order to perform CRUD and other operations.
- JUnit and Mockito: Used to test the API
- Maven: Used to manage the dependencies of the application

## Prerequisites
- JDK 17 or higher must be installed.
- Apache Maven 3.6 or newer.

## Dependencies
- Spring Boot: Used to develop a java-based, standalone, Spring-based application
- GSON: Used for JSON serialization and deserialization.
- SpringFox Swagger UI: Used to generate interactive API documentation, allowing API calls to be used directly in the browser.
- 
## Installation
1. Clone the repository
```bash
git clone git@github.com:cbfacademy/java-rest-api-assessment-kirstyabhus.git
```
2. Navigate to the projects directory
```bash
cd java-rest-api-assessment
```
3. Build the project with maven
```bash
mvn clean install
```

## Running the Application
After installing, you can run the application using the following Maven command.

  ```bash
  mvn spring-boot:run
  ```

Upon running this command, the application can be accessed with the API endpoints defined in the Swagger documentation.
<p align="center">
  <img src="Swagger_Endpoints.gif" alt="swagger documentation demo" width="500" height="263"/>
</p>

## Testing
The following test script can be used to test the application:
```bash
mvn test
```
### Currently Working On
- Allowing users to view real-time market news for stock 
