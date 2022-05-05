package ml.simplecontactsapi.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.reactive.RestPath;

import io.smallrye.mutiny.Uni;
import ml.simplecontactsapi.dao.Contact;
import ml.simplecontactsapi.service.ContactService;

@Path("/rest/contacts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContactRestResource {
    private final ContactService _contactService;

    @Inject
    public ContactRestResource(ContactService contactService) {
        _contactService = contactService;
    }

    @GET
    public Uni<List<Contact>> getAllContacts() {
        return _contactService.getAllContacts();
    }

    @GET
    @Path("{id}")
    public Uni<Contact> getContactById(@RestPath Long id) {
        return _contactService.getContactById(id);
    }

    @POST
    public Uni<Contact> addContact(Contact c) {
        return _contactService.addContact(c);
    }

    @PUT
    @Path("{id}")
    public Uni<Contact> updateContact(@RestPath Long id, Contact c) {
        return _contactService.updateContact(id, c);
    }

    @DELETE
    @Path("{id}")
    public Uni<Boolean> deleteContact(@RestPath Long id) {
        return _contactService.deleteContact(id);
    }
}