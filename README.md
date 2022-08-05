## Musalasoft Drones Code Interview

This is a java spring project
Maven is used in dependency management

# Stack

![](https://img.shields.io/badge/h2-database-✓-blue.svg) 
![](https://img.shields.io/badge/java_17-✓-blue.svg)
![](https://img.shields.io/badge/spring_boot-✓-blue.svg)
![](https://img.shields.io/badge/swagger_3-✓-green.svg)

The following Technology stack was used in this project :

**Java 17** : Core language used

**SpringBoot 3.0** : Java Framework

**H2-database** : Internal in-memory Database


#Design Pattern Used

Strategy Design Pattern

# Steps To Run

**1** - Do a `git clone` of the project : 

**2** - Open application.properties , set the default profile to run { dev / live / uat } for this project no profiles were set

**4** - Run the project from the IDE or from terminal using `java 17` or newer with the following command `java -jar service.jar`

**5** - Access service swagger documentation `http://localhost:86/api/swagger.html`


# Package Structure  - com.mulsalasoft.interview.drones

**1** - entities : This package contains all jpa database tables used in this project

**2** - models : This package contains all Enums, Application Request and Response Objects,including external objects request and response objects for external calls


# Deploying the application

After setting the application profile in the `application.properties` files, you can build the project using `mvn clean install`.

You can also run tests associated with this project using `mvn test` to make sure all the test cases are passed although the `mvn clean install` would also run test
as it builds the project.

Once the build is complete you will find a `.jar` file inside the **target** folder to run the jar files use the following command `java -jar operation-0.0.1-SNAPSHOT.jar`.

**NOTE** :  you need java installed on your environment to be able to run this project.

[[_TOC_]]

---

:scroll: **START**


### Introduction

There is a major new technology that is destined to be a disruptive force in the field of transportation: **the drone**. Just as the mobile phone allowed developing countries to leapfrog older technologies for personal communication, the drone has the potential to leapfrog traditional transportation infrastructure.

Useful drone functions include delivery of small items that are (urgently) needed in locations with difficult access.

---

### Task description

We have a fleet of **10 drones**. A drone is capable of carrying devices, other than cameras, and capable of delivering small loads. For our use case **the load is medications**.

A **Drone** has:
- serial number (100 characters max);
- model (Lightweight, Middleweight, Cruiserweight, Heavyweight);
- weight limit (500gr max);
- battery capacity (percentage);
- state (IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING).

Each **Medication** has: 
- name (allowed only letters, numbers, ‘-‘, ‘_’);
- weight;
- code (allowed only upper case letters, underscore and numbers);
- image (picture of the medication case).

Develop a service via REST API that allows clients to communicate with the drones (i.e. **dispatch controller**). The specific communicaiton with the drone is outside the scope of this task. 

The service should allow:
- registering a drone;
- loading a drone with medication items;
- checking loaded medication items for a given drone; 
- checking available drones for loading;
- check drone battery level for a given drone;

> Feel free to make assumptions for the design approach. 

---

### Requirements

While implementing your solution **please take care of the following requirements**: 

#### Functional requirements

- There is no need for UI;
- Prevent the drone from being loaded with more weight that it can carry;
- Prevent the drone from being in LOADING state if the battery level is **below 25%**;
- Introduce a periodic task to check drones battery levels and create history/audit event log for this.

---

#### Non-functional requirements

- Input/output data must be in JSON format;
- Your project must be buildable and runnable;
- Your project must have a README file with build/run/test instructions (use DB that can be run locally, e.g. in-memory, via container);
- Required data must be preloaded in the database.
- JUnit tests are optional but advisable (if you have time);
- Advice: Show us how you work through your commit history.

---

:scroll: **END**
