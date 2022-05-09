package ml.simplecontactsapi.graphql;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import io.smallrye.mutiny.Uni;
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
    public Uni<List<Contact>> getAllContacts() {
        return _contactService.getAllContacts();
    }

    @Mutation("addMoreContact")
    public Uni<Boolean> addContact(final Contact c) {
        return _contactService.addContact(c);
    }
}
