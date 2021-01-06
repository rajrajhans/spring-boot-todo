# Spring Todo
A simple todo application with the backend as a REST API developed using Spring Boot, and the frontend as a simple React app.

Intended as an exercise to learn Spring Boot.

### API Endpoints
Here is a short summary about the available REST API Endpoints for the functionalities that have been implemented.

| <b>Method</b> |     <b>Resource</b>     |                  <b>Description</b>                 |
|:-------------:|:-----------------------:|:----------------------------------------------------|
|     `GET`     |         `/todos`        | Get all todos                                       |
|     `GET`     |      `/todos/{id}`      | Get specific todo with that id                      |
|     `GET`     | `/todos/user/{user_id}` | Get all todos by that user                          |
|     `POST`    |        `/todos/`        | Create a new todo (todo object in Request Body)     |
|     `PUT`     |     `/todos/{ id }`     | Update that todo (todo object in Request Body)      |
|     `GET`     |         `/users`        | Get all users                                       |
|     `PUT`     |     `/users/{ id }`     | Update user details                                 |
|     `POST`    |         `/users`        | Create a new user (user object in Request Body)     |
|    `DELETE`   |     `/todos/{ id }`     | Delete that todo                                    |
|    `DELETE`   |     `/users/{ id }`     | Delete that user (and the todos associated with it) |

### Tools used
- Spring Boot 2
- Spring Framework 5
- Spring Data JPA
- Hibernate
- Spring MVC
- Apache Maven
- MySQL  
- IDE: IntelliJ Ultimate

### For more details, please check out this blog post. 