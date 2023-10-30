# Pet Store
This is a RESTful API for a pet store built with Java Spring Boot and JPA, providing endpoints for managing users, pets, and their interactions.

## Prerequisites
Before you start, make sure you have the following prerequisites installed on your system:

- Java: Download and install Java
- Maven: Download and install Maven
- IDE (optional): Use an Integrated Development Environment like Eclipse, IntelliJ, or Visual Studio Code.

## Installation
1. Clone this repository to your local machine:
``` bash
git clone https://github.com/yourusername/pet-store-spring-boot-jpa.git
```

2. Open the project in your IDE or navigate to the project directory using the terminal.

3. If you want to use a PostgreSQL database instead of the default H2 in-memory database, open the application.properties file and change the property:
```application.properties
spring.profiles.active=h2
```
to
```application.properties
spring.profiles.active=postgres
```

4. Build the project using Maven:
```bash
mvn clean install
```

## Running the App
You can run the application using your IDE or via the command line. To start the application from the command line, use the following command:
```bash
mvn spring-boot:run
```
The application will start and listen on port 8080.

## API Endpoints
### List Users
Endpoint: `GET /api/users`
This endpoint returns a list of all users in the pet store.

### List Pets
Endpoint: `GET /api/pets`
This endpoint returns a list of all pets in the pet store.

### Create Users
Endpoint: `POST /api/users`
This endpoint creates 10 new users in the pet store, with different
(random) properties/attributes).

### Create Pets
Endpoint: `POST /api/pets`
This endpoint creates 10 new pets in the pet store, with different
(random) properties/attributes).

### Buy Pets
Endpoint: `PUT /api/users/buy`
This endpoint goes over all the users and tries to buy a pet of the store for each user.

### History Log
Endpoint: `GET /api/users/purchase-log`
This endpoint provides the purchase history of all the buy commands with three columns:
- Date of execution
- Number of users that successfully bought a pet
- Number of users that were not allowed to buy any pet

## Testing
Testing
The project includes unit and integration tests for the services and repositories. You can run the tests in the IDE, or using the following command:
```bash
mvn test
```




