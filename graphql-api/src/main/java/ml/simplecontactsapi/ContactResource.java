package ml.simplecontactsapi;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.graphql.api.Subscription;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor;
import io.vertx.core.cli.annotations.Description;

@GraphQLApi
@ApplicationScoped
public class ContactResource {
    private final ContactRepository _contactRepository;
    private final BroadcastProcessor<Contact> _addedContactsProcessor;
    private final BroadcastProcessor<Contact> _updatedContactsProcessor;
    private final BroadcastProcessor<Long> _deletedContactsProcessor;

    @Inject
    public ContactResource(ContactRepository contactRepository) {
        this._contactRepository = contactRepository;
        this._addedContactsProcessor = BroadcastProcessor.create();
        this._updatedContactsProcessor = BroadcastProcessor.create();
        this._deletedContactsProcessor = BroadcastProcessor.create();
    }

    @Query("allContacts")
    @Description("Get all contacts from the database")
    public Uni<List<Contact>> getAllContacts() {
        return this._contactRepository.listAll();
    }

    @Query("getContactById")
    @Description("Get a contact by ID")
    public Uni<Contact> getContactById(@Name("contactId") Long id) {
        return this._contactRepository.findById(id);
    }

    @Query("getContactByName")
    @Description("Get a contact by name")
    public Uni<List<Contact>> getContactByName(@Name("name") String name) {
        return this._contactRepository.findByName(name);
    }

    @Query("searchContactByName")
    @Description("Search a contact by name")
    public Uni<List<Contact>> searchContactByName(@Name("name") String name) {
        return this._contactRepository.searchByName(name);
    }

    @Query("getContactByEmail")
    @Description("Get a contact by email")
    public Uni<List<Contact>> getContactByEmail(@Name("email") String email) {
        return this._contactRepository.findByEmail(email);
    }

    @Query("searchContactByEmail")
    @Description("Search a contact by email")
    public Uni<List<Contact>> searchContactByEmail(@Name("email") String email) {
        return this._contactRepository.searchByEmail(email);
    }

    @Mutation("addContact")
    @Description("Add a contact")
    public Uni<Contact> addContact(final Contact c) {
        Uni<Contact> uc = Panache.<Contact>withTransaction(c::persist);
        this._addedContactsProcessor.onNext(c);
        return uc;
    }

    @Subscription("addedContact")
    public Multi<Contact> contactAdded() {
        System.out.println("Subscription is getting called for addedContact");
        return this._addedContactsProcessor;
    }

    @Mutation("updateContact")
    @Description("Update a contact")
    public Uni<Contact> updateContact(@Name("contactId") Long id, final Contact c) {
        Uni<Contact> uc = Panache.withTransaction(
                () -> Contact.<Contact>findById(id).onItem().ifNotNull().invoke(entity -> entity.updateContact(c)));
        this._updatedContactsProcessor.onNext(c);
        return uc;
    }

    @Subscription("updatedContact")
    public Multi<Contact> contactUpdated() {
        System.out.println("Subscription is getting called for updatedContact");
        return this._updatedContactsProcessor;
    }

    @Mutation("deleteContact")
    @Description("Delete a contact")
    public Uni<Boolean> deleteContact(@Name("contactId") Long id) {
        Uni<Boolean> isDeleted = Panache.withTransaction(() -> Contact.deleteById(id));

        // wait for confirmation of deletion
        // _deletedContactsProcessor.onNext(isDeleted.await().indefinitely());
        this._deletedContactsProcessor.onNext(id);

        return isDeleted;
    }

    @Subscription("deletedContact")
    public Multi<Long> contactDeleted() {
        System.out.println("Subscription is getting called for deletedContact");
        return this._deletedContactsProcessor;
    }
}
