package ml.simplecontactsapi.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor;
import ml.simplecontactsapi.dao.Contact;
import ml.simplecontactsapi.model.ContactList;

@ApplicationScoped
public class ContactService {
    private final ContactList _contactList;
    private final BroadcastProcessor<Contact> _addedContactsProcessor;
    private final BroadcastProcessor<Contact> _updatedContactsProcessor;
    private final BroadcastProcessor<Integer> _deletedContactsProcessor;

    @Inject
    public ContactService(final ContactList cl) {
        _contactList = cl;
        _addedContactsProcessor = BroadcastProcessor.create();
        _updatedContactsProcessor = BroadcastProcessor.create();
        _deletedContactsProcessor = BroadcastProcessor.create();
    }

    public Uni<List<Contact>> getAllContacts() {
        return Uni.createFrom().item(_contactList.getContactList());
    }

    public Uni<ContactList> getContactList() {
        return Uni.createFrom().item(_contactList);
    }

    public Uni<Contact> getContactById(int id) {
        return Uni.createFrom().item(_contactList.getContactList().get(id));
    }

    public Uni<List<Contact>> getContactByName(String name) {
        Stream<Contact> allContacts = _contactList.getContactList().stream();

        return Uni.createFrom().item(allContacts
                .filter(
                        c -> {
                            if (c.getFirstName() != null && c.getFirstName().equalsIgnoreCase(name)) {
                                return true;
                            } else if (c.getMiddleName() != null && c.getMiddleName().equalsIgnoreCase(name)) {
                                return true;
                            } else if (c.getLastName() != null && c.getLastName().equalsIgnoreCase(name)) {
                                return true;
                            } else {
                                return false;
                            }
                        })
                .collect(Collectors.toList()));
    }

    public Uni<List<Contact>> searchContactByName(String name) {
        Stream<Contact> allContacts = _contactList.getContactList().stream();

        return Uni.createFrom().item(allContacts
                .filter(
                        c -> c.getFullName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList()));
    }

    public Uni<List<Contact>> getContactByEmail(String email) {
        Stream<Contact> allContacts = _contactList.getContactList().stream();

        return Uni.createFrom().item(allContacts
                .filter(
                        c -> c.getEmail().equalsIgnoreCase(email))
                .collect(Collectors.toList()));
    }

    public Uni<List<Contact>> searchContactByEmail(String email) {
        Stream<Contact> allContacts = _contactList.getContactList().stream();

        return Uni.createFrom().item(allContacts
                .filter(
                        c -> c.getEmail().toLowerCase().contains(email.toLowerCase()))
                .collect(Collectors.toList()));
    }

    public Uni<Boolean> addContact(final Contact c) {
        _addedContactsProcessor.onNext(c);
        return Uni.createFrom().item(_contactList.getContactList().add(c));
    }

    public Multi<Contact> contactAddedSubscription() {
        System.out.println("Subscription is getting called for addedContact");
        return _addedContactsProcessor;
    }

    public Uni<Boolean> addContactList(ContactList cl) {
        List<Contact> listOfContacts = cl.getContactList();

        return Uni.createFrom().item(_contactList.getContactList().addAll(listOfContacts));
    }

    public Uni<Contact> updateContact(int id, final Contact c) {
        _updatedContactsProcessor.onNext(c);

        return Uni.createFrom().item(_contactList.getContactList().set(id, c));
    }

    public Multi<Contact> contactUpdatedSubscription() {
        System.out.println("Subscription is getting called for updatedContact");
        return _updatedContactsProcessor;
    }

    public Uni<Boolean> deleteContact(Integer id) {
        _deletedContactsProcessor.onNext(id);

        Contact c = _contactList.getContactList().remove((int) id);

        if (c != null) {
            return Uni.createFrom().item(true);
        } else {
            return Uni.createFrom().item(false);
        }
    }

    public Multi<Integer> contactDeletedSubscription() {
        System.out.println("Subscription is getting called for deletedContact");
        return _deletedContactsProcessor;
    }
}
