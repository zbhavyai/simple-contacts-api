package ml.simplecontactsapi.graphql;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import io.smallrye.mutiny.Uni;
import io.vertx.core.cli.annotations.Description;
import ml.simplecontactsapi.dao.Contact;
import ml.simplecontactsapi.service.ContactService;

@GraphQLApi
public class ContactGraphqlResourceAdditional {
    private final ContactService _contactService;

    @Inject
    public ContactGraphqlResourceAdditional(ContactService contactService) {
        _contactService = contactService;
    }

    @Query("allContactsOnceAgain")
    @Description("Get all contacts from the database")
    public Uni<List<Contact>> getAllContacts() {
        return _contactService.getAllContacts();
    }

    @Mutation("addMoreContact")
    @Description("Add a contact")
    public Uni<Contact> addContact(final Contact c) {
        return _contactService.addContact(c);
    }
}
