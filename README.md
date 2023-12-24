# user-management-api

Assumptions :
- roles cannot be added unless there's a user to add the role to.
- Permissions cannot be added unless there's a role to add them to.
- security access is not based on the permissions entity since @PreAuthrize annotation is used, it is based on the role.
- security and caching is applied to API and Application entities only.
- there's a one-to-one relationship between the API and Application entity
