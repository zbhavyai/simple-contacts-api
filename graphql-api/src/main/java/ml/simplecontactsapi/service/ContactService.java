package ml.simplecontactsapi.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor;
import ml.simplecontactsapi.dao.Contact;
import ml.simplecontactsapi.repository.ContactRepository;

@ApplicationScoped
public class ContactService {
    private final ContactRepository _contactRepository;
    private final BroadcastProcessor<Contact> _addedContactsProcessor;
    private final BroadcastProcessor<Contact> _updatedContactsProcessor;
    private final BroadcastProcessor<Long> _deletedContactsProcessor;

    @Inject
    public ContactService(ContactRepository contactRepository) {
        _contactRepository = contactRepository;
        _addedContactsProcessor = BroadcastProcessor.create();
        _updatedContactsProcessor = BroadcastProcessor.create();
        _deletedContactsProcessor = BroadcastProcessor.create();
    }

    public Uni<List<Contact>> getAllContacts() {
        return _contactRepository.listAll();
    }

    public Uni<Contact> getContactById(Long id) {
        return _contactRepository.findById(id);
    }

    public Uni<List<Contact>> getContactByName(String name) {
        return _contactRepository.findByName(name);
    }

    public Uni<List<Contact>> searchContactByName(String name) {
        return _contactRepository.searchByName(name);
    }

    public Uni<List<Contact>> getContactByEmail(String email) {
        return _contactRepository.findByEmail(email);
    }

    public Uni<List<Contact>> searchContactByEmail(String email) {
        return _contactRepository.searchByEmail(email);
    }

    public Uni<Contact> addContact(final Contact c) {
        Uni<Contact> uc = Panache.<Contact>withTransaction(c::persist);
        _addedContactsProcessor.onNext(c);
        return uc;
    }

    public Multi<Contact> contactAddedSubscription() {
        System.out.println("Subscription is getting called for addedContact");
        return _addedContactsProcessor;
    }

    public Uni<Contact> updateContact(Long id, final Contact c) {
        Uni<Contact> uc = Panache.withTransaction(
                () -> Contact.<Contact>findById(id).onItem().ifNotNull().invoke(entity -> entity.updateContact(c)));
        _updatedContactsProcessor.onNext(c);
        return uc;
    }

    public Multi<Contact> contactUpdatedSubscription() {
        System.out.println("Subscription is getting called for updatedContact");
        return _updatedContactsProcessor;
    }

    public Uni<Boolean> deleteContact(Long id) {
        Uni<Boolean> isDeleted = Panache.withTransaction(() -> Contact.deleteById(id));

        // wait for confirmation of deletion
        // _deletedContactsProcessor.onNext(isDeleted.await().indefinitely());
        _deletedContactsProcessor.onNext(id);

        return isDeleted;
    }

    public Multi<Long> contactDeletedSubscription() {
        System.out.println("Subscription is getting called for deletedContact");
        return _deletedContactsProcessor;
    }
}
