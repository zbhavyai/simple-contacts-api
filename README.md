# GraphQL API and Subscriptions demo

A very simple GraphQL API and GraphQL Client for contacts stored on a local postgres database.

## Dependencies

- JDK version 11.0.11 or above
- Maven version 3.6.3 or above
- PostgreSQL server version 13.4 or above

## How to run

1. Clone the repository on your machine

2. Start the PostgreSQL server on your local machine. If you are accessing the PostgreSQL server over a network, edit the property `quarkus.datasource.reactive.url` in the file `application.properties` and replace `localhost` with the server IP address.

3. Connect to your PostgreSQL server using a user like that has create DB and create user privileges, like `postgres`.

4. Run the script [`init.sql`](documents/init.sql) on the PostgreSQL server. This script will create a user `quarkustest` with password `sqlquarkustest` and a database `quarkustest`.

5. To run the GraphQL API, execute the [`run_graphql_api.sh`](run_graphql_api.sh) script. It will launch the quarkus in dev mode at port 5005, and GraphQL API will be available at [`http://localhost:8080/graphql`](http://localhost:8080/graphql).

6. To test the GraphQL endpoints, load the file [SimpleContactsGraphQL.postman_collection.json](documents/SimpleContactsGraphQL.postman_collection.json) in Postman and execute the API calls.

7. To run the GraphQL Client, execute the [`run_graphql_client.sh`](run_graphql_client.sh) script. It will launch the quarkus in dev mode at port 5006, and GraphQL API query results will be available at [`http://localhost:8081/dynamic`](http://localhost:8081/dynamic).

8. To test the subscription of GraphQL client, visit the URL [`http://localhost:8081/dynamic/subscription`](http://localhost:8081/dynamic/subscription), and try adding new contacts using Postman.
