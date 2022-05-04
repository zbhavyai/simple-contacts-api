package ml.simplecontactsapi;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import io.vertx.core.cli.annotations.Description;

@GraphQLApi
@ApplicationScoped
public class ContactAdditionalResource {
    private final ContactRepository _contactRepository;

    @Inject
    public ContactAdditionalResource(ContactRepository contactRepository) {
        this._contactRepository = contactRepository;
    }

    @Query("allContactsOnceAgain")
    @Description("Get all contacts from the database")
    public Uni<List<Contact>> getAllContacts() {
        return this._contactRepository.listAll();
    }

    @Mutation("addMoreContact")
    @Description("Add a contact")
    public Uni<Contact> addContact(final Contact c) {
        Uni<Contact> uc = Panache.<Contact>withTransaction(c::persist);
        return uc;
    }
}
