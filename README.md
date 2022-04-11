# A simple demo of API for Quarkus

A very simple REST API and GraphQL API performing CRUD on contacts stored on local postgres database.

## Dependencies

- JDK version 11.0.11 or above
- Maven version 3.6.3 or above
- PostgreSQL server version 13.4 or above

## How to run

1. Clone the repository on your machine

2. Start the PostgreSQL server on your local machine. If you are accessing the PostgreSQL server over a network, edit the property `quarkus.datasource.reactive.url` in the file `application.properties` and replace `localhost` with the server IP address.

3. Connect to your PostgreSQL server using a user like that has create DB and create user privileges, like `postgres`.

4. Run the script [`init.sql`](documents/init.sql) on the PostgreSQL server. This script will create a user `quarkustest` with password `sqlquarkustest` and a database `quarkustest`.

5. Running the below command from the repository's root directory will get the API running.

   ```bash
   $ mvn quarkus:dev
   ```

6. To test the REST endpoints, load the file [SimpleContactsAPI.postman_collection.json](documents/SimpleContactsAPI.postman_collection.json) in Postman and execute the API calls.

7. To test the GraphQL endpoints, load the file [SimpleContactsGraphQL.postman_collection.json](documents/SimpleContactsGraphQL.postman_collection.json) in Postman and execute the API calls.
