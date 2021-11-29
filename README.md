# Mediscreen

Mediscreen specializes in detecting risk's factor for disease. Our screenings using predictive analysis of patient populations at an affordable cost. This app is composed of several microservices and uses
Java to run and stores the data in Mysql and Mongo DB.

## Architecture dagram

![Alt text](https://i.postimg.cc/rsBXK371/Mediscreen-architecture.jpg)

## Get started

These instructions will help you get a copy of the web application prototype on your local computer for version 1
development.

### Prerequisites

What things you need to install the software and how to install them

- Java 11
- Maven 3.9.1
- Spring Boot 2.5.6
- Mysql 8.0.23
- MongoDb 5.0

### Installing

A step by step series of examples that tell you how to get a development env running:

1.Install Java:

https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html

2.Install Maven:

https://maven.apache.org/install.html

3.Install MySql:

https://dev.mysql.com/downloads/mysql/

4.Install MongoDb

https://www.mongodb.com/try?jmp=nav#community

After downloading the mysql 8 installer and installing it, you will be asked to configure the password for the default `root` account.
This code uses the default root account to connect and the password can be set as `rootroot`. If you add another user/credentials make sure to change the same in the code base.

## Running App

Import the code, open the console, go to the folder that contains the pom.xml file, then execute the below command to launch the application.

`mvn spring-boot:run`

## Docker deployment

1.Generate a jar file for each java microservice with:

`mvn clean package`

2.Go to the respective folder that contains the Dockerfile of each microservice and to build the images type:

`docker build -t client_ui:1.0 .`

`docker build -t patient_report:1.0 .`

`docker build -t patient_note:1.0 .`

`docker build -t patient_info:1.0 .`

3.Get the image of MongoDb :

`docker pull mongo:latest`

4.Get the image of MySQL :

`docker pull mysql:8.0`

5.Using the command prompt, navigate to the folder that contains the docker-compose.yml then run the following command to deploy all mediscreen microservices:

`docker-compose up -d`
