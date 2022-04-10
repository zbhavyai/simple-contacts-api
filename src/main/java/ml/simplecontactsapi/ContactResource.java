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
    @Inject
    private ContactRepository _contactRepository;

//    @GET
//    @Path("/count")
//    @Produces(MediaType.TEXT_PLAIN)
//    public Uni<Long> contactsCount() {
//        return _contactRepository.count();
//    }

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

    private void copyContact(Contact destination, Contact source) {
        destination.setNamePrefix(source.getNamePrefix());
        destination.setFirstName(source.getFirstName());
        destination.setMiddleName(source.getMiddleName());
        destination.setLast_name(source.getLast_name());
        destination.setNameSuffix(source.getNameSuffix());
        destination.setNickname(source.getNickname());
        destination.setCompany(source.getCompany());
        destination.setDepartment(source.getDepartment());
        destination.setTitle(source.getTitle());
        destination.setPhone(source.getPhone());
        destination.setEmail(source.getEmail());
        destination.setDateOfBirth(source.getDateOfBirth());
        destination.setWebsite(source.getWebsite());
        destination.setNotes(source.getNotes());
        destination.setLabel(source.getLabel());
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
                        .invoke(entity -> copyContact(entity, c)))
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