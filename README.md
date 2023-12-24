# user-management-api

Assumptions :
- roles cannot be added unless there's a user to add the role to.
- Permissions cannot be added unless there's a role to add them to.
- Applications connot be created unless there's an API to add to.
- There's a one-to-one relationship between the API and Application entity
- security access is not based on the permissions entity since @PreAuthrize annotation is used, it is based on the role.
- security and caching is applied to API and Application entities only.
- basic authentication is used (user,password).

Deployment instructions : 
- install mySql and redis based on the configurations in application.properties.
- an admin user is needed to post,put,delete APIs and Applications.
- a developer user is needed to get APIs and Applications.
- use Postman basic authentication to login with created user
