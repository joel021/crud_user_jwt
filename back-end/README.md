# Back-end using Spring Boot framework

<h2>Setup</h2>
- Download the project or use $git clone git@github.com:joel021/crud_user_jwt.git
- Go to <i>src/main/resources/</i>
- Create <i>application.properties</i> file using <i> application.properties.example</i> as example
- Change the values of applicable parameters on the file, as <i>spring.datasource.username</i> and <i>spring.datasource.password</i>
- If you prefer, its possible to create a enviromment variable and use it on <i>application.properties</i> file. For example, create DB_PASSWORD and assign <i>spring.datasource.password</i> as ${DB_PASSWORD}.
- Install java 17 or greater on your machine
- on project root level, run:
    - $ mvn clean spring-boot:run, to run the project
    - $ mvn clean install, to install the project in your envromment

<h3>To run using Java 8</h3>

<p>This project was developed using Java 17 with spring boot 3. However, it can be built using Java 8.</p>

- Install Java 8 on your machine
- Go to https://start.spring.io/ and create new project choosing Java 8, Maven and Spring Boot 2.7.
- Copy this project classes files to the new project
- Handle the dependencies

