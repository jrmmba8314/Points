# Points

A project for the Fetch Rewards Coding Exercise - Backend Software Engineering.

## Executing Webservice
An executable jar file is provided. It is are located under the subdirectory **/exec** . To run the application use the command
<br>**java -jar points.jar**
<br>
A windows executable file is also provided under the same subdirectory. You can double-click on **points.exe** to run that webservice on Windows.
<br>Note that Java version 11 or higher must be installed on your machine.


Alternatively, the webservice is being hosted on AWS at https://jrmmba.com/points.

## Using the webservice
A Postman collection is included that can be used to access and manipulate the data. The Postman collection is set to run
against the AWS implementation.

A quick reference for the endpoints:
- GET [http://localhost:2022/points/transactions](http://localhost:2022/points/transactions) or [http://jrmmba.com/points/transactions](http://jrmmba.com/points/transactions) 
  - returns all transaction
- POST [http://localhost:2022/points/earn](http://localhost:2022/points/earn) or [http://jrmmba.com/points/earn](http://jrmmba.com/points/earn) 
  - adds a transaction
- POST [http://localhost:2022/points/spend](http://localhost:2022/points/spend) or [http://jrmmba.com//points/spend](http://jrmmba.com//points/spend)
  - reward points being spent are recorded
- GET [http://localhost:2022/points/balance](http://localhost:2022/points/balance) or [http://jrmmba.com/points/balance](http://jrmmba.com/points/balance) 
  - current balance by payer

More detailed documentation can be found using the Swagger documentation mentioned below. Note that using HTTP instead 
of the secured HTTPs is a requirement of the project.

## Running the unit tests
To run the unit tests, you must have Java 11 and Maven installed on your computer. To execute the test, You can run 
the following command from the folder containing the *.pom file for the project found under the subfolder **/points**.
<br>**mvn test**

Tests are only implemented for Controllers and Services specific to this implementation.
- The exception handling process is a drop in process that has been tested elsewhere.
- Repositories are generally not tested as they are tied directly to the database and thus are difficult to separate out
as a unit.

## Documentation
### Swagger
Swagger documentation for the API is available at [http://jrmmba.com/points/swagger-ui.html](http://jrmmba.com/points/swagger-ui.html) .
For this project I am using just the default Swagger documentation. Each item in the Swagger 
documentation can be customized.

### Javadocs
Javadocs documentation for the application code is available at [https://jrmmba8314.github.io/Points/](https://jrmmba8314.github.io/Points/)

## Notes on implementation
The application is written using Java 11 with the Spring Framework and Spring Boot.

### Exception handling
I included an application level exception handling process. I developed this process and like to include it in my 
Java Spring Applications. Some code in the process may not be relevant to the current application, but I wanted to 
show the complete process.

This process is helpful to both the developer and client (frontend) through standardization of the error messages and 
centralized handling of exceptions. 
- A developer just needs to throw an exception in their code.
The application will take care of processing that exception and reporting to the client.
- A client will always get a uniformed JSON object of the error message. Thus, the client can write 
a generic error handling process as well, reducing their development time. 

## H2 database
Durable data is not required for this application - for now. However, the H2 database can be configured to run in memory.
The advantage is that the developer has access to all the useful functions of a relational database while enjoying
the performance of an in memory data structure. Also, if the application does grow and needs durable data, 
a simple configuration change can switch between H2 and full feature database such at PostgreSQL.

### Audit fields
Although the requirements for the application did not specifically mention audit fields, I decided to include them.
Any time a database table is created, at the minimum audit fields should be included in a professional application.

### Model fields
I purposely made my model fields all lowercase. Any variable that will end up as a database column
I like to keep all lowercase. Spring does a nice job of converting fields to columns if the field is all
lowercase. Ways exist to work around this issue, but I have always felt the additional configuration, annotations,
and coding needed was not worth the gain. I just use lowercase and move on.
