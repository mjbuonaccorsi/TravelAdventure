# Travel Adventure
Rest Service API running in Spring Boot protected by Apache Shiro. 

Required:

Local instance of MongoDB running with user ID "adventure" and password "abc123"

use admin
db.createUser(
   {
     user: "adventure",
     pwd: "abc123",
     roles: [ "readWrite", "dbAdmin" ]
   }
)