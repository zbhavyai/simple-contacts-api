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
import ml.simplecontactsapi.dao.Contact;
import ml.simplecontactsapi.model.ContactList;
import ml.simplecontactsapi.service.ContactService;

@GraphQLApi
public class ContactGraphqlResource {
    private final ContactService _contactService;

    @Inject
    public ContactGraphqlResource(ContactService contactService) {
        _contactService = contactService;
    }

    @Query("allContacts")
    public Uni<List<Contact>> getAllContacts() {
        return _contactService.getAllContacts();
    }

    @Query("contactList")
    public Uni<ContactList> getContactList() {
        return _contactService.getContactList();
    }

    @Query("getContactById")
    public Uni<Contact> getContactById(@Name("contactId") Integer id) {
        return _contactService.getContactById(id);
    }

    @Query("getContactByName")
    public Uni<List<Contact>> getContactByName(@Name("name") String name) {
        return _contactService.getContactByName(name);
    }

    @Query("searchContactByName")
    public Uni<List<Contact>> searchContactByName(@Name("name") String name) {
        return _contactService.searchContactByName(name);
    }

    @Query("getContactByEmail")
    public Uni<List<Contact>> getContactByEmail(@Name("email") String email) {
        return _contactService.getContactByEmail(email);
    }

    @Query("searchContactByEmail")
    public Uni<List<Contact>> searchContactByEmail(@Name("email") String email) {
        return _contactService.searchContactByEmail(email);
    }

    @Mutation("addContact")
    public Uni<Boolean> addContact(final Contact c) {
        return _contactService.addContact(c);
    }

    @Mutation("addContactList")
    public Uni<Boolean> addContactList(@Name("contactList") final ContactList cl) {
        return _contactService.addContactList(cl);
    }

    @Subscription("addedContact")
    public Multi<Contact> contactAdded() {
        return _contactService.contactAddedSubscription();
    }

    @Mutation("updateContact")
    public Uni<Contact> updateContact(@Name("contactId") Integer id, final Contact c) {
        return _contactService.updateContact(id, c);
    }

    @Subscription("updatedContact")
    public Multi<Contact> contactUpdated() {
        return _contactService.contactUpdatedSubscription();
    }

    @Mutation("deleteContact")
    public Uni<Boolean> deleteContact(@Name("contactId") Integer id) {
        return _contactService.deleteContact(id);
    }

    @Subscription("deletedContact")
    public Multi<Integer> contactDeleted() {
        return _contactService.contactDeletedSubscription();
    }
}
