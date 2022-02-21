# Points

A project for the Fetch Rewards Coding Exercise - Backend Software Engineering.

## Executing Webservice
Executable apps are provided for Windows, Mac, and Linux. These can found under the subdirectory **/exec** .
Run apps as you normally would launch on your operating system, usually by double clicking on the file. 
Java does not have to be installed on your computer. The necessary Java runtime files are bundled with the app.
The webservice is set to run locally on port 2022.

Alternatively, the webservice is being hosted on AWS at https://jrmmba.com/points.

## Using the webservice
A Postman collection is included that can be used to access and manipulate the data. 
A variable in the collection can be changed to use either a local implementation of the webservice or the AWS implementation.
The collection defaults to AWS.

A quick reference for the endpoints:

More detailed documentation can be found using the Swagger documentation mentioned below.

## Running the unit and integration tests
Unit and Integration tests are included for this application. To run the unit and integration tests, 
you must have Java 11 and Maven installed on your computer. You can run the following command from the root folder of the
project to execute the test.
**maven tests**

## Documentation
### Swagger
Swagger documentation for the API is available at https://jrmmba.com/points/swagger-ui.html .
For this project I am using just the default Swagger documentation. Each item in the Swagger 
documentation can be customized.

### Javadocs
Javadocs documentation for the application code is available at https://jrmmba.com/points/javadocs

## Notes on implementation

The application is written using Java 11 with the Spring Framework and Spring Boot.

### Exception handling
I included an application level exception handling process. I developed this process and like to include it in my 
Java Spring Applications. Some code in the process may not be relevant to the current application, but I wanted to 
show the complete process.

This process is helpful to both the developer and client (frontend) through standardization of the error messages and 
centralized handling of exceptions. A developer just needs to throw an exception in their code.
The application will take care of processing that exception and reporting to the client. 
A client will always get a uniformed JSON object of the error message. Thus, the client can write 
a generic error handling process as well, reducing their development time. 

## H2 database
Persistent data is not required for this application - for now. However, the H2 database can be configured to run in memory.
The advantage is that the developer has access to all the useful functions of a relational database while enjoying
the performance of an in memory data structure. Also, if the application does grow and needs persistent data, 
a simple configuration change can switch between H2 and full feature database such at PostgreSQL.

### Audit fields
Although the requirements for the application did not specifically mention audit fields, I decided to include them.
Any time a database table is created, at the minimum audit fields should be included for a professional application.

### Model fields
I purposely made my model fields all lowercase. Any variable that will end up as a database column
I like to keep all lowercase. Spring does a nice job of converting fields to columns if the field is all
lowercase. Ways exist to work around this issue, but I have allows felt the additional configuration, annotations,
and coding needed was not worth the gain. I just use lowercase and move on.
