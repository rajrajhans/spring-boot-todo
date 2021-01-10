# SpringTodo
A simple todo application REST API developed using Spring Boot.

Used Spring Data JPA for CRUD operations on a MySQL database, and Spring Security to implement JWT based authentication and authorization. Learned about implemented OAuth with Github, Facebook, Google & Twitter as providers. Finally, deployed the application using AWS Elastic Beanstalk with EC2 and RDS.  

Intended as an exercise to learn Spring Boot.

<b>For more details, please check out [this blog post](https://rajrajhans.com/2021/01/beginners-guide-to-spring-boot/). </b>

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

### Challenges & Learnings
- **JWT Implementation** - Earlier, I had implemented a JWT based authentication solution in another project using Python Flask. But, using Spring Boot I observed that I had more granular control over several aspects, which the Flask JWT library I had used just abstracted it for me. So I learned more about JWT. 
- **OAuth2 Integration** - Initially, I implemented email & password based authentication & authorization, and later decided to add OAuth2 based authentication & authorization. To integrate the OAuth2 flow with my existing auth flow and models was a challenge. To know more about the final  OAuth flow, visit (this section)[#oauth-flow] of the readme.
- **Deploying Spring Boot Applications** - I had worked with Heroku before, so this time, I decided to try out Amazon EC2 and RDS for deployment. I used Elastic Beanstalk to make it easier for managing EC2 and RDS instances.
    - Setting up Security Groups to make sure EC2 and RDS can talk to each other was a challenge due to AWS's old documentation that no longer holds in the new console.
    - I frequently ended up getting a JDBC Communications Link Failure Error, but I learned a bit about how AWS VPC works.
- **Spring Boot** - Learned about the Spring ecosystem and paradigms.

### OAuth Flow

Integrating OAuth2.0 for Authentication and Authorization was one of the challenges in this project. Here, I have described what exactly happens in authenticating the user using an OAuth provider. 

- User visits `/oauth2/authorization/<provider>` and is redirected by Spring Security's OAuth2 Client to the provider's OAuth endpoint. Provider can be `github`, `facebook`, `google` or `twitter`.
- The user now allows or denies the permission on the provider's page.
    - If the user allows, 
        - then provider will redirect the user to the callback URL with an `authorization_code`. 
        - Then, Spring Security will exchange the `authorization_code` for an `access_token` and invoke the `OAuth2UserService` (as specified in `SecurityConfig`)
        - The `OAuth2UserService` retrieves the details of the authenticated user (using appropriate `OAuth2UserInfo` according to the provider in question) and then it will create a new User or update existing user in database based on the email received from the provider.
        - Once this is done, our `oAuth2SuccessHandler` is invoked by Spring Security. There, I'm creating a JWT token (using our `JwtUtil`) based on the `UserDetails` object we now have with us as the `Principal` in Spring Security's `Authentication` object, and then, the user is redirected to `targetURL` along with the token attached.
        
- If the user denies, 
    - they will be redirected to same callback URL but with an error. 
  - Spring Security will then invoke `OAuth2FailureHandlerService` (as specified in `SecurityConfig`).

### Tools used
- Spring Boot 2
- Spring Framework 5
- Spring Data JPA
- Hibernate
- Spring MVC
- Apache Maven
- MySQL
- AWS Elastic Beanstalk, EC2 & RDS for deployment
