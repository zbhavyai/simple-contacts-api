package ml.simplecontactsclient;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestPath;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.graphql.client.GraphQLClient;
import io.smallrye.graphql.client.Response;
import io.smallrye.graphql.client.core.Document;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClient;
import io.smallrye.mutiny.Uni;
import static io.smallrye.graphql.client.core.Document.document;
import static io.smallrye.graphql.client.core.Argument.arg;
import static io.smallrye.graphql.client.core.Argument.args;
import static io.smallrye.graphql.client.core.Field.field;
import static io.smallrye.graphql.client.core.Operation.operation;

@Path("/")
public class ContactClient {
    @Inject
    @GraphQLClient("contacts-dynamic")
    DynamicGraphQLClient dynamicClient;

    @GET
    @Path("/dynamic")
    @Produces(MediaType.APPLICATION_JSON)
    @Blocking
    public Uni<Response> getAllContactsUsingDynamicClient() throws Exception {
        Document query = document(
                operation(
                        field("allContacts",
                                field("firstName"),
                                field("phone"),
                                field("email"),
                                field("lastName"))));
        // Response response = dynamicClient.executeSync(query);
        // return response.getData();

        return this.dynamicClient.executeAsync(query);
    }

    @GET
    @Path("/dynamic/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @Blocking
    public Uni<Response> getAllContactsByNameUsingDynamicClient(@RestPath String name) throws Exception {
        Document query = document(
                operation(
                        field("getContactByName", args(arg("name", name)),
                                field("firstName"),
                                field("nickname"),
                                field("phone"),
                                field("email"),
                                field("lastName"))));

        return this.dynamicClient.executeAsync(query);
    }
}
