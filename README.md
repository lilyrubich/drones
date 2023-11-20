## Drones

A simple Spring boot application that demonstrates the usage of RESTful API using Spring boot, Hibernate and H2.

## Tools and Technologies used

* Java 20.0.2
* Spring boot 3.1.4
* H2
* JPA 3.1.4
* Hibernate
* Maven
* JUnit

## Steps to install

**1. Run the app**

You can run the spring boot app by typing the following command -

```bash
mvn spring-boot:run
```

You can also package the application in the form of a `jar` file and then run it like so -

```bash
mvn package
java -jar target/crudapi-0.0.1-SNAPSHOT.jar
```

The server will start on port 8080.

**2. Open H2 console**

Database details are available in H2 console at the link below

```bash
http://localhost:8080/h2-console
```

Fill out the opened form with the following data:

```bash
JDBC URL: jdbc:h2:mem:dronesdb
User name: sa
*No password*
```

## Explore Rest APIs

The app defines following APIs.
```
GET /medications?droneId={droneId}

Example URL: http://localhost:8080/medications?droneId=3
```
```
GET /checkAvailableDrones

Example URL: http://localhost:8080/checkAvailableDrones
```
```
GET /checkDroneBatteryLevel?droneId={droneId}

Example URL: http://localhost:8080/checkDroneBatteryLevel?droneId=1
```   
```
POST /registerDrone

Example URL: http://localhost:8080/registerDrone
Example Request body:

{
    "serialNumber": "TURBO_Drone3000",
    "batteryCapacity": "0.6",
    "state":"IDLE",
    "model":"Heavyweight"
}
```
```
POST /loadingDroneWithMedications

Example URL: http://localhost:8080/loadingDroneWithMedications
Example Request body:

{
    "droneId": "2",
    "medicationIds": ["5", "6"]
}
```

You can test them using postman or any other rest client.