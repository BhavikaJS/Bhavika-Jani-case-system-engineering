___________________________________________
Cloud Surfer - User Management Service

This is a java based application that allows various CRUD operations to be performed for a user account.
___________________________________________
About the Application

Users can register and login to the application.
Apart from this, modifications such as Password Reset, Forgot Password, Updating the user details and deleting the user (account) is also enabled.
Application validates the user and the action to be performed via Exception handling process.

Validation Criteria
1. Email id should be unique in the database
2. Password should be off minimum 5 characters.
3. All fields are mandatory, except for lastName.
4. Updates are possible only if the user is present in the database and credentials provided is correct.
___________________________________________
Install & Running

Prerequisites
Java 1.8 - Programming language
Maven 3.6.1 - Build tool
SpringBoot 2.1.2 

Build & run
Run test
$ mvn test
Run the web server on dev mode
$ mvn spring-boot:run

Built With
Maven - Dependency Management