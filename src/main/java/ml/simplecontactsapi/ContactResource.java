package ml.simplecontactsapi;

import java.net.URI;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;

@Path("/api/contacts")
public class ContactResource {
    @Inject
    private ContactRepository _contactRepository;

    @GET
    @Path("/count")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<Long> contactsCount() {
        return _contactRepository.count();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<Contact>> getAllContacts() {
        return _contactRepository.listAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Contact> getContactById(Long id) {
        return _contactRepository.findById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> addContact(Contact c) {
        c.setId(null);

        return Panache.<Contact>withTransaction(c::persist).onItem()
                .transform(inserted -> Response.created(URI.create("/api/contacts/" + inserted.getId())).build());
    }

//    @DELETE
//    @Path("/{id}")
//    public Uni<Response> deleteContact(Long id) {
//        Uni<Boolean> deleteByIdOperation = _contactRepository.deleteById(id);
//
////        deleteByIdOperation.
//
//        Uni.createFrom().item(1);
//
//        return Panache.<Contact>(c::delete).onItem()
//                .transform(inserted -> Response.created(URI.create("/api/contacts/" + inserted.getId())).build());
//    }
}
