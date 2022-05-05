package ml.simplecontactsapi.graphql;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

import io.smallrye.graphql.api.Subscription;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.cli.annotations.Description;
import ml.simplecontactsapi.dao.Contact;
import ml.simplecontactsapi.service.ContactService;

@GraphQLApi
public class ContactGraphqlResource {
    private final ContactService _contactService;

    @Inject
    public ContactGraphqlResource(ContactService contactService) {
        _contactService = contactService;
    }

    @Query("allContacts")
    @Description("Get all contacts from the database")
    public Uni<List<Contact>> getAllContacts() {
        return _contactService.getAllContacts();
    }

    @Query("getContactById")
    @Description("Get a contact by ID")
    public Uni<Contact> getContactById(@Name("contactId") Long id) {
        return _contactService.getContactById(id);
    }

    @Query("getContactByName")
    @Description("Get a contact by name")
    public Uni<List<Contact>> getContactByName(@Name("name") String name) {
        return _contactService.getContactByName(name);
    }

    @Query("searchContactByName")
    @Description("Search a contact by name")
    public Uni<List<Contact>> searchContactByName(@Name("name") String name) {
        return _contactService.searchContactByName(name);
    }

    @Query("getContactByEmail")
    @Description("Get a contact by email")
    public Uni<List<Contact>> getContactByEmail(@Name("email") String email) {
        return _contactService.getContactByEmail(email);
    }

    @Query("searchContactByEmail")
    @Description("Search a contact by email")
    public Uni<List<Contact>> searchContactByEmail(@Name("email") String email) {
        return _contactService.searchContactByEmail(email);
    }

    @Mutation("addContact")
    @Description("Add a contact")
    public Uni<Contact> addContact(final Contact c) {
        return _contactService.addContact(c);
    }

    @Subscription("addedContact")
    public Multi<Contact> contactAdded() {
        return _contactService.contactAddedSubscription();
    }

    @Mutation("updateContact")
    @Description("Update a contact")
    public Uni<Contact> updateContact(@Name("contactId") Long id, final Contact c) {
        return _contactService.updateContact(id, c);
    }

    @Subscription("updatedContact")
    public Multi<Contact> contactUpdated() {
        return _contactService.contactUpdatedSubscription();
    }

    @Mutation("deleteContact")
    @Description("Delete a contact")
    public Uni<Boolean> deleteContact(@Name("contactId") Long id) {
        return _contactService.deleteContact(id);
    }

    @Subscription("deletedContact")
    public Multi<Long> contactDeleted() {
        return _contactService.contactDeletedSubscription();
    }
}
