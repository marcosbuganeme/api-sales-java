BACKEND JAVA USANDO SPRING BOOT

2 tipos de credenciais

user: <b>user</b>
<br>
password: <b>user</b>

user: <b>admin</b>
<br>
password: <b>admin</b>


# Sales Force Application

This is a Sales Force Management application developed using Spring Boot. The project is designed to help businesses manage their sales activities, track sales performance, and streamline their sales processes.

## Prerequisites

Before you start, make sure you have the following installed:
- JDK 11 or later
- Maven 3.6 or later

## Project Setup

To set up this project locally, follow these steps:

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/sales-force-app.git
   cd sales-force-app
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
   The application will be available at `http://localhost:8080`.

## Project Structure

- `src/main/java`: Contains the Java source files including models, repositories, services, and controllers.
- `src/main/resources`: Configuration files and static resources.
- `src/test/java`: Contains the unit and integration tests.

## Features

- Manage sales teams and individual salespeople.
- Track sales targets and achievements.
- Generate reports on sales performance.
- User authentication and authorization.

## Testing

To run the tests:

```bash
mvn test
```

## Deployment

To deploy the application, package it into a runnable JAR file:

```bash
mvn package
java -jar target/api-sales-java-1.0.0.jar
```

Execute o seguinte comando para realizar um build da aplicação via Docker

```bash
docker build -t api-sales-java:tag .
```

Para executar a aplicação

```bash
docker run -p 8080:8080 api-sales-java:latest
```

## Contributions

Contributions are welcome. Please fork the repository and submit pull requests to the main branch.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE) file for details.

## Contact

Marcos Buganeme – [@marcosbuganeme](https://twitter.com/marcosbuganeme) – molavosbdeveloper@gmail.com

Project Link: https://github.com/marcosbuganeme/api-sales-java
