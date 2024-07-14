# The Record Shop

This is the API for The Record Shop stocking app, which allows a user to get all albums in stock. The user can also get an album by its ID or a list of albums by a given artist, genre, or release year. Finally, the user can add a new album, update or delete an existing album, or get information about a specific album by its name.

## Getting Started

For a comprehensive documentation on the API and its endpoints, please see the SwaggerUI page here: [The Record Shop](http://recordshopapi-env.eba-gmyh6wzs.eu-west-2.elasticbeanstalk.com/api/v1/albums/docs). 
Also see "Running locally" for notes on how to get a copy of the project and run it on your local machine testing purposes.

## Using the API

### Web-browser access
The application has already been deployed to the cloud, so you can consume or interact with the API directly from your web browser using the following base URL: 

```
recordshopapi-env.eba-gmyh6wzs.eu-west-2.elasticbeanstalk.com/api/v1/albums
```

To make POST and PUT requests to the API, you will need an API tool like [Postman](https://www.postman.com/). All other requests can be made from a web browser. 


### Running locally

Aside from interacting with the cloud-hosted application, you can also run the application quickly on your local machine by using your in-memory database. Follow these steps:


1. Fork and clone the repo.

2. Open the application.properties file and change the active profile to "dev" from "rds".

```
spring.profiles.active=dev
```

3. Clear the username and password from the application-dev.properties file.

```
spring.datasource.username=
spring.datasource.password=
```

4. Run the application and test the endpoint to get all albums from your browser.

```
http://recordshopapi-env.eba-gmyh6wzs.eu-west-2.elasticbeanstalk.com/api/v1/albums/
```
5. The initial output will be an empty array.
```
[]
```
6. You will need to make some POST requests to add data to the in-memory database while the application runs on your local machine. Remember to use [Postman](https://www.postman.com/) to make (test) all POST and PUT requests.

## Deployment

If you need help with creating a locally persisted Postgres database for the application or deploying it to the cloud, please reach out to me.

## Built With

* [Spring Boot](https://spring.io/projects/spring-boot) - The Java framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Docker](https://www.docker.com/) - Used to containerize the application for cloud deployment
* [AWS RDS](https://aws.amazon.com/rds/) - Used to persist the database on the cloud
* [AWS Elastic Beanstalk](https://aws.amazon.com/elasticbeanstalk/) - Used to host the application on the cloud
* [SwaggerUI/Open API](https://swagger.io/tools/swagger-ui/) - Used to document the API

## Contributing

Please reach out if you would like to contribute to this project.

## Author

* **Israel Peters** - *Entire Project* - [israelopeters](https://github.com/israelopeters)


## Acknowledgments

* Many thanks to my Northcoders tutors and fellow cohort engineers for the many lessons and help with troubleshooting WSL.
* A big thank you to [PurpleBooth](https://github.com/PurpleBooth) for providing an easy-to-use template for writing a good README.md.
