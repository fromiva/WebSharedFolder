# Notes for developers

The application is a Spring Boot 3 Java web-application with main dependencies:

- Java Development Kit version 17 or higher
- Apache Maven wrapper-based build system
- embedded Apache Tomcat web server
- embedded HyperSQL Database management engine
- Spring-powered Liquibase database migration tool
- Spring-powered Thymeleaf HTML template engine
- Bootstrap 5 as an HTML framework

Code organization and structure rules:

- Codebase divided by layers: persistence, service and presentation
- Liquibase's migration scripts have the YAML format
- Documentation files have the Markdown format
- Code style is checked by CheckStyle

## Maven, Spring Boot, and Liquibase Profiles

The application has the following Spring Boot profiles:

- 'production': default profile to run uber JAR file directly
- 'development': profile to use while the development process,
drop and rebuild the file-based database on each application start
- 'testing': profile to run unit tests with in-memory database configuration

Any Spring Boot profile has a corresponding both Maven and Liquibase profile.
When the application starts from Maven, it uses Maven profile to initiate
the bound Spring Boot one. Default Maven profile is testing.

## Building from source

To build the application from source, use the following Maven Wrapper command in a terminal:

```shell
# For Linux
./mvnw clean package
```

```shell
# For Windows
./mvnw.cmd clean package
```
