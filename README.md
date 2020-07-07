## POC Contacts

Practice project of Spring Boot.

`Contact` and `Avatar` has individual APIs, should be accessed separately.


### Maven Build

Use `mvn clean package` to build project.


### Docker Build

Use `sudo docker build -t poc-contacts:1.0.0 .` to build docker.


### Run Docker

Use `sudo docker run -d -p 8080:8080 -t poc-contacts:1.0.0` to run docker.


### Swagger URL

Swagger API document: `http://{host}:{port}/swagger-ui.html`
