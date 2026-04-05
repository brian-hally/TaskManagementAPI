# Task Management API 

# Project Overview
A simple Java Enterprise web application that provides a RESTful API for managing tasks with prioritization capabilities.

### Prerequisites
- Java 11
- Maven 3.6.3 or higher
- Git
- Postman (Optional)


## How to Run
These commands will build the project and run it locally. They can be run using command prompt or any other shell, usually from your IDE terminal.

### Option 1: Using Maven Wrapper (Recommended as it doesn't need Maven installed on your machine)

```bash
./mvnw clean install
./mvnw spring-boot:run
```

### Option 2: Using Local Maven

```bash
mvn clean install
mvn spring-boot:run
```

###  Testing the API
- Running the API as a springboot configuration locally by using `mvn spring-boot:run` or './mvnw spring-boot:run' preferably.
- Once the application will start on `http://localhost:8181` as that is the port I have set in the application.properties file.
- From there you can test the API using a REST client, I recommend Postman.
- In Postman, you will have to add basic authentication with the username and password you have set in the application.properties file.
- Once you have added a task, you will be able to test the other endpoints as there will be data in the DB.
- If you re-run the application, the data will not be persisted with a H2 database.


### Trade-offs:
- **Pros**: Simple, fast, easy to understand and extend while ensuring urgent tasks are prioritized. 
- **Cons**: Does not guarantee an optimal solution (may not maximize the number of tasks or use all available time) because it is a greedy algorithm.


## Assumptions
1. **Time-based priority**: Tasks with earlier due dates are more important.
2. **In-memory database**: Data is lost when the application stops (suitable for demo/testing). 
3. **No authentication**: API is publicly accessible, could be enhanced with something like KeyCloak.
4. **Simple greedy approach**: Very Basic greedy algorithm, could be more complex but suits requirements.
5. **Validation**: Basic validation ensures title, status, assignee, created by and reasonable time values.
