# user-management-api

# Assumptions
- roles cannot be added unless there's a user to add the role to.
- Permissions cannot be added unless there's a role to add them to.
- Applications connot be created unless there's an API to add to.
- There's a one-to-one relationship between the API and Application entity
- security access is not based on the permissions entity since @PreAuthrize annotation is used, it is based on the role.
- security and caching is applied to API and Application entities only.
- basic authentication is used (user,password).

# Deployment instructions
- install mySql and redis based on the configurations in application.properties.
- an admin user is needed to post,put,delete APIs and Applications.
- a developer user is needed to get APIs and Applications.
- use Postman basic authentication to login with created user

# JSON Objects to use in request body

User :
{
    "username":"amdin1",
    "pass":"password"
}

Role : 

{
    "role":"ADMIN"
}

Permission : 

{
    "permission":"CREATE"
}

API : 

{
    "name":"API",
    "url":"cars",
    "method":"POST",
    "secure":"true",
    "authorizationMethod":"method"
}

Application : 

{
"name":"carsApp",
"status":"running"
}

# API paths and requirements

User : 

POST : localhost:8080/user
include a JSON user object request body.

GET : localhost:8080/user/{userid}
include a user id in the path.

PUT : localhost:8080/user/{userid}
include a user id on the path and a JSON user object request body.

DELETE : localhost:8080/user/{userid}
include a user id on the path.

Role:

POST : localhost:8080/user/{userId}/roles
include user id in the path and a JSON role object request body.

GET : localhost:8080/user/{userid}/roles
get all the user roles, include a user id in the path.

GET : localhost:8080/roles/{roleId}
get specific roles, incluse a role id in the path.

PUT : locahhost:8080/roles/{roleId}
include a role id in the path and a JSON role object request body.

DELETE : localhost:8080/user/{userId}/roles/{roleId}
include both user id and role id in the path

Permission :

POST : localhost:8080/roles/{id}/permissions
include a role id in the path and a JSON permissions object request body.

GET : localhost:8080/roles/{id}/permissions
get all permission of a role, include a role id in the path.

GET : localhost:8080/permissions/{permissionId}
get a specific permission, include a permission id in the path

PUT : localhost:8080/permissions/{permissionId}
include a permissions id in the path and a JSON permissions object request body.

DELETE : localhost:8080/roles/{id}/permissions/{permissionId}
delete a permission from a role, include a role id and permissions id.

API : 

POST : localhost:8080/api
[requires admin permission]
include a JSON API object in request body.

GET : localhost:8080/api/all
[requires developer permission]
no data required.

GET : localhost:8080/api/{id}
[requires developer permission]
get a specific id, include an api id in the path.

PUT : localhost:8080/api/{id}
[requires admin permission]
include an API id in the path and a JSON API object in request body.

DELETE : localhost:8080/api/{id}
[requires admin permission]
include an API id in the path.

Application  :

POST : localhost:8080/api/{id}/application
[requires admin permission]
include an API id and a JSON aplication object in request body.


GET : localhost:8080/application/all
[requires developer permission]
no data required.

GET : localhost:8080/application/{id}
[requires developer permission]
get a specific id, include an application id in the path.

PUT : localhost:8080/application/{id}
[requires admin permission]
include an application id in the path.

DELETE : localhost:8080/application/{id}
[requires admin permission]
include an application id in the path.

# Security  
using spring-boot-starter-security dependency, types of security applied :

1- password encoding 
password for the User entitiy are encoded before pesisting into the database using the passwordEncoder() initilized in security configuration.
APIUserDetails and APIUserDetailsService were used to map users asking for authorization from the database to UserDetails object with authorities being mapped from the user's role association.

2- authorization for access
API and Application paths both require authorization to access.
First the @EnableMethodSecurity annotiation was used in the security configuration to enable authrization on method level, the @PreAuthorize was used on controller methods and the role {"ADMIN","DEVELOPER"} was specified for each method to control access.

# Cashing 
Redis was used for the caching using the spring-boot-starter-data-redis dependency.
redis was installed in my local machine then configured in application.properties.
the annotation @Cacheable was used in GET methods to cache the responces.
@CacheEvict was used in POST and PUT methods to erase cache for new results.
returned objects (API and Application) has to implement the Serializable interface in order to be cached.
The following snippet shows the memory of redis before caching list of APIs, then after caching, then after using @CacheEvict

![image](https://github.com/shahad1401/user-management-api/assets/70811433/cf8be737-3c94-419e-ac1c-ba25ac0d5ced)

