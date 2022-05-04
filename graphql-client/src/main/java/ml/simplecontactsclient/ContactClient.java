package ml.simplecontactsclient;

import static io.smallrye.graphql.client.core.Argument.arg;
import static io.smallrye.graphql.client.core.Argument.args;
import static io.smallrye.graphql.client.core.Document.document;
import static io.smallrye.graphql.client.core.Field.field;
import static io.smallrye.graphql.client.core.Operation.operation;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.reactive.RestPath;

import io.smallrye.graphql.client.GraphQLClient;
import io.smallrye.graphql.client.Response;
import io.smallrye.graphql.client.core.Document;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClient;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
@Path("/")
public class ContactClient {
    @Inject
    @GraphQLClient("contacts-dynamic")
    DynamicGraphQLClient dynamicClient;

    @GET
    @Path("/dynamic/contacts")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getAllContactsUsingDynamicClient() throws Exception {
        Document query = document(
                operation(field("allContacts", field("firstName"), field("phone"), field("email"), field("lastName"))));
        // Response response = dynamicClient.executeSync(query);
        // return response.getData();

        return this.dynamicClient.executeAsync(query);
    }

    @GET
    @Path("/dynamic/contacts/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getAllContactsByNameUsingDynamicClient(@RestPath String name) throws Exception {
        Document query = document(operation(field("getContactByName", args(arg("name", name)), field("firstName"),
                field("nickname"), field("phone"), field("email"), field("lastName"))));

        return this.dynamicClient.executeAsync(query);
    }

    /**
     * Only to activate the web socket just by visiting the endpoint
     *
     * @return
     */
    @GET
    @Path("/dynamic/subscription")
    @Produces(MediaType.APPLICATION_JSON)
    public Multi<Response> subscribeToAdd() throws Exception {

        // Document query = document(operation(field("addedContact", field("company"),
        // field("phone"), field("email"))));
        String queryString = "subscription test {\naddedContact {\ncompany\nfirstName\n}\n}";
        // this.dynamicClient.executeAsync(new RequestImpl(document(operation(
        // field("subscribeToAdd", field("addedContact", field("company"),
        // field("phone"), field("email")))))));
        return this.dynamicClient.subscription(queryString);

        // return this.dynamicClient.executeAsync(query);
        // return this.dynamicClient.subscription(query);

        /*
         * final ContactSubscription clientEndPoint = new ContactSubscription();
         * clientEndPoint.addMessageHandler(new ContactSubscription.MessageHandler() {
         *
         * @Override public void handleMessage(String message) {
         * System.out.println(message); } });
         *
         * clientEndPoint.sendMessage("addedContact {\ncompany\n}\n}");
         */

        // return Uni.createFrom().voidItem();
    }
}
