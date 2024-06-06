# Pay My Buddy

Application that allows sending money between buddies.
  
## Summary

On the Pay My Buddy application, it is possible to :
* Create an account
* Log in with this account
* Search and add buddies with their email address to send them money
* Add funds to the account

There are also :
* A contact page, redirecting on mailbox to send an email is something goes wrong, or in case of any question
* A page dedicated to update user's firstname and lastname.

---
## Getting started

### Technologies used : 
* Java version : 17
* Maven 4.0.0
* Spring Boot 3.2.3
* Thymeleaf
* Spring Security
* Spring Data JPA
* Lombok
* Bootstrap 5.3.3

### Run Pay My Buddy

```git clone https://github.com/Clabb2029/paymybuddy.git```

And open your IDE, use the command above to clone the project, and run the application to launch it.

## Application Diagrams

### UML Diagram
Representing model class structure.  
  
![UML_diagram](https://github.com/Clabb2029/paymybuddy/assets/97224689/d077ee39-2f91-4392-a99e-94724cb1d1c2)

### Physical data model
Representing relational data objects and their relationships.  
  
![Physical_data_model](https://github.com/Clabb2029/paymybuddy/assets/97224689/145fd33a-7ff1-4fc0-90eb-2737cae3ee95)


## Database

To use the application, it is necessary to create the database.
To create the database, run the script located here : 
[src/main/ressources/database/create.sql](src/main/resources/database/create.sql)

It is possible to populate the database by running the script located here :  
[src/main/ressources/database/data.sql](src/main/resources/database/data.sql)  
or by running the application and creating new accounts.



