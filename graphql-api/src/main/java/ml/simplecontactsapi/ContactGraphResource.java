package ml.simplecontactsapi;

import java.util.List;
import javax.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;
import io.smallrye.graphql.api.Context;
import io.smallrye.graphql.api.Subscription;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor;
import io.vertx.core.cli.annotations.Description;
import io.quarkus.hibernate.reactive.panache.Panache;

@GraphQLApi
public class ContactGraphResource {
    private ContactRepository _contactRepository;
    private Context _context;

    BroadcastProcessor<Contact> processor = BroadcastProcessor.create();

    @Inject
    public ContactGraphResource(ContactRepository contactRepository, Context context) {
        _contactRepository = contactRepository ;
        _context = context;
    }

    @Query("allContacts")
    @Description("Get all contacts from the database")
    public Uni<List<Contact>> getAllContacts() {
        return _contactRepository.listAll();
    }

    @Query("getContactById")
    @Description("Get a contact by ID")
    public Uni<Contact> getContactById(@Name("contactId") Long id) {
        return _contactRepository.findById(id);
    }

    @Query("getContactByName")
    @Description("Get a contact by name")
    public Uni<List<Contact>> getContactByName(@Name("name") String name) {
        return _contactRepository.findByName(name);
    }

    @Query("searchContactByName")
    @Description("Search a contact by name")
    public Uni<List<Contact>> searchContactByName(@Name("name") String name) {
        return _contactRepository.searchByName(name);
    }

    @Query("getContactByEmail")
    @Description("Get a contact by email")
    public Uni<List<Contact>> getContactByEmail(@Name("email") String email) {
        return _contactRepository.findByEmail(email);
    }

    @Query("searchContactByEmail")
    @Description("Search a contact by email")
    public Uni<List<Contact>> searchContactByEmail(@Name("email") String email) {
        return _contactRepository.searchByEmail(email);
    }

    @Mutation("addContact")
    @Description("Add a contact")
    public Uni<Contact> addContact(Contact c) {
        processor.onNext(c);
        Uni<Contact> uc = Panache.<Contact>withTransaction(c::persist);
        return uc;
    }

    @Subscription
    public Multi<Contact> contactAdded() {
        System.out.println("Subscription is getting called");
        return processor;
    }

    @Mutation("updateContact")
    @Description("Update a contact")
    public Uni<Contact> updateContact(@Name("contactId") Long id, Contact c) {
        return Panache.withTransaction(
                () -> Contact.<Contact>findById(id).onItem().ifNotNull().invoke(entity -> entity.updateContact(c)));
    }

    @Mutation("deleteContact")
    @Description("Delete a contact")
    public Uni<Boolean> deleteContact(@Name("contactId") Long id) {
        return Panache.withTransaction(() -> Contact.deleteById(id));
    }
}
