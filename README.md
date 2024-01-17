#Notification System Project

This is a notification system project built using the following technologies: Kafka, Docker, Flyway, Spring Security, JWT, Hibernate, Lombok and Spring Boot.

---
#####Overview

The Notification System Project provides a set of RESTful APIs for user authentication, notification delivery, and management of templates and contacts. It utilizes Kafka for real-time messaging, Docker for containerization, Flyway for database migration, Spring Security with JWT for authentication, Hibernate for database interactions, and Spring Boot for the application framework.

---

#####Key Features

**User Authentication:** Users can authenticate themselves by providing their credentials through the login endpoint.

**User Registration:** New users can register with a username, email, and password.

**Notification Delivery:** The system supports real-time notification delivery through email and Telegram.

**Template Management:** Users can create and manage notification templates.

**Contacts Management:** Users can manage their contacts and load contacts from CSV files.

**Admin Privileges:** Administrators have the ability to manage both contacts and users in the system.

**Integration with Telegram Bot:** Contacts loaded from CSV files should subscribe by email provided by csv file sender to the Telegram bot to receive telegram notification *@EmergencyNotifierSystemBot* .

example of telegram subscription :
![Снимок экрана 2024-01-17 в 17 46 24](https://github.com/nikitagagua/Notyfication-System/assets/110698480/7a5e641a-fd33-4a5d-94c7-84419bcc044f)


----
#####Running the Project

*To run the project using Docker, follow these steps:*

```java
git clone https://github.com/nikitagagua/Notyfication-System.git

mvn clean package

docker-compose up -d
```
---

#####Without Docker
*To run the project without Docker, follow these steps:*

```java
git clone https://github.com/nikitagagua/Notyfication-System.git

mvn clean package

java -jar target/NotyficationSystem-0.0.1-SNAPSHOT.jar

```

----

#####Accessing the APIs

Once the project is running, you can access the APIs at http://localhost:8080 for the local application and http://localhost:8081 for the docker-based notification service.

---

#####API Documentation

Below are some of the available endpoints and their descriptions:

**User Authentication**

######Login:

POST /auth/login - Authenticate a user by providing a username and password.

```json
{
    "username":"john.doe@example.com",
    "password":"password123"
}
```

If you successfully log in, you will receive a JWT authentication token for further authentication
for example:

```json
{
    "token":
        "eyJhbGciOiJIUzI1NiJ9.
        eyJzdWIiOiJqb2huLmRvZUBleGFtcGxlLmNvbSIsImlhdCI6M
        TcwNTQzOTI1MiwiZXhwIjoxNzA2NjM5MjUyfQ.
        cmDE9KrGHksp3VRVht4Yzt7ubJ-Icr7_gV6gcXFAC4o"
}
```

######Register:
POST /auth/register - Register a new user with a username, email, and password.

```json
{
    "username": "some-name",
    "email": "somemeail@gmail.com",
    "password": "some-password"
}
```

If you successfully registered, you will find out message on email and should go via link there,
this link contains 
###### Confirmation Request
GET request:
/auth/confirm-account?token=..your-token..

Link example:

http://localhost:8081/auth/confirm-account?token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyZ2FndWExMjFAZ21haWwuY29tIiwiaWF0IjoxNzA1MTgwNTA0LCJleHAiOjE3MDYzODA1MDR9.iHg0_T36IqwO61jFT-V-kAKadKE6uuALCC8tqv6LKWQ

Result example if user confirmed:

```json
    200 OK
```

----

#####Notification Management

Export All: 

POST /exportAll - Export data using a file upload, body of requst should contain a CSV file with contacts

Notify All: 
GET /notify/all - Notify all contacts both way.

Notify via Telegram: 
GET /telegram/sendNotification - Send a notification via Telegram.
result example:
![Снимок экрана 2024-01-17 в 17 45 08](https://github.com/nikitagagua/Notyfication-System/assets/110698480/fe79f9fc-df61-4cc1-a3c6-250e9dc1a8b1)


Notify via Email: 
GET /email/sendNotification - Send a notification via Email.

result example:
![Снимок экрана 2024-01-17 в 17 38 53](https://github.com/nikitagagua/Notyfication-System/assets/110698480/26f3e83d-a611-4a70-85b0-1951897fb2f8)


----

#####User and Template Management
Create Template:
PUT /see-my-templates - Create a new notification template.

See My Templates:
GET /see-my-templates - View your notification templates.

Get All Templates:
GET /getAllPattens - Get all notification templates.

Get User by ID: 
GET /users/{id} - Get user details by ID.

Update User by ID: 
PUT /users/{id} - Update user details by ID.

Get All Users: 
GET /users/all - Get all users.

Get User by Email: 
GET /users/email/{email} - Get user details by email.

Delete User: 
DELETE /users/{id} - Delete a user by ID.

Get All Contacts: 
GET /contacts/all - Get all contacts.

Get Contact by ID: 
GET /contacts/email/{email} - Get contact details by email.

Delete Contact: 
DELETE /contacts/{id} - Delete a contact by ID.
Postman Collection

You can import the provided Postman collection file (notify_system_endpoints.postman_collection.json) to easily test the API endpoints. The collection includes sample requests for various API actions.
