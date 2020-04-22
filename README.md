# spring-boot-aws-react

Learning about Spring Boot with AWS and ReactJS from [Spring Boot and React JS | Full Course | 2020](https://www.youtube.com/watch?v=i-hoSg8iRG0&t=1592s)

## Requirement

1. Java 11
2. nodeJS v12.x
3. AWS Account for use AWS S3.

## Configuration

Before running application, You have to modify **application.properties**

1. **aws.credentials.access-key**: Your AWS access key
2. **aws.credentials.secret-key**: Your AWS secret key

## Run application

1. Spring Boot

```shell
$ mvn spring-boot:run
```

The backend running on <http://localhost:8080>

2. ReactJS

```shell
$ cd src/main/frontend
$ npm start
```

The frontend running on <http://localhost:3000>
