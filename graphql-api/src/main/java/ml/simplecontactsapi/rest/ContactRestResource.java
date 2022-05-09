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
import ml.simplecontactsapi.model.ContactList;
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
    @Path("contactList")
    public Uni<ContactList> getContactList() {
        return _contactService.getContactList();
    }

    @GET
    @Path("{id}")
    public Uni<Contact> getContactById(@RestPath Integer id) {
        return _contactService.getContactById(id);
    }

    @GET
    @Path("getContactByName/{name}")
    public Uni<List<Contact>> getContactByName(@RestPath String name) {
        return _contactService.getContactByName(name);
    }

    @GET
    @Path("searchContactByName/{name}")
    public Uni<List<Contact>> searchContactByName(@RestPath String name) {
        return _contactService.searchContactByName(name);
    }

    @GET
    @Path("getContactByEmail/{email}")
    public Uni<List<Contact>> getContactByEmail(@RestPath String email) {
        return _contactService.getContactByEmail(email);
    }

    @GET
    @Path("searchContactByEmail/{email}")
    public Uni<List<Contact>> searchContactByEmail(@RestPath String email) {
        return _contactService.searchContactByEmail(email);
    }

    @POST
    public Uni<Boolean> addContact(Contact c) {
        return _contactService.addContact(c);
    }

    @POST
    @Path("addContactList")
    public Uni<Boolean> addContactList(final ContactList cl) {
        return _contactService.addContactList(cl);
    }

    @PUT
    @Path("{id}")
    public Uni<Contact> updateContact(@RestPath Integer id, Contact c) {
        return _contactService.updateContact(id, c);
    }

    @DELETE
    @Path("{id}")
    public Uni<Boolean> deleteContact(@RestPath Integer id) {
        return _contactService.deleteContact(id);
    }
}