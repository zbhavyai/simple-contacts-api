package ml.simplecontactsapi;

import java.net.URI;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import org.jboss.resteasy.reactive.RestPath;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;

@Path("/api/contacts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContactResource {
    private ContactRepository _contactRepository;

    @Inject
    public ContactResource(ContactRepository cr) {
        _contactRepository = cr;
    }

    @GET
    public Uni<List<Contact>> getAllContacts() {
        return _contactRepository.listAll();
    }

    @GET
    @Path("{id}")
    public Uni<Response> getContactById(@RestPath Long id) {
        return _contactRepository.findById(id).onItem()
                .transform(contact -> contact != null ? Response.ok(contact) : Response.status(Status.NOT_FOUND))
                .onItem().transform(ResponseBuilder::build);
    }

    @POST
    public Uni<Response> addContact(Contact c) {
        if (c == null) {
            throw new WebApplicationException("Cannot add empty contact", 422);
        }

        return Panache.<Contact>withTransaction(c::persist).onItem()
                .transform(inserted -> Response.created(URI.create("/api/contacts/" + inserted.getId())).build());
    }

    @PUT
    @Path("{id}")
    public Uni<Response> updateContact(@RestPath Long id, Contact c) {
        if (c == null) {
            throw new WebApplicationException("Cannot add empty contact", 422);
        }

        System.out.println(c);

        return Panache
                .withTransaction(() -> Contact.<Contact>findById(id).onItem().ifNotNull()
                        .invoke(entity -> entity.updateContact(c)))
                .onItem().ifNotNull().transform(entity -> Response.ok(entity).build()).onItem().ifNull()
                .continueWith(Response.ok().status(Status.NOT_FOUND)::build);
    }

    @DELETE
    @Path("{id}")
    public Uni<Response> deleteContact(@RestPath Long id) {
        return Panache.withTransaction(() -> Contact.deleteById(id))
                .map(deleted -> deleted ? Response.ok().status(Status.NO_CONTENT).build()
                        : Response.ok().status(Status.NOT_FOUND).build());
    }
}
