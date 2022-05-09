package ml.simplecontactsapi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ml.simplecontactsapi.dao.Contact;

@ApplicationScoped
public class ContactList {
    private List<Contact> contactList = new ArrayList<>();

    public ContactList() {
    }

    @JsonCreator
    public ContactList(
            @JsonProperty("contactList") final List<Contact> contactList) {
        this.contactList.addAll(contactList);
    }

    public List<Contact> getContactList() {
        return this.contactList;
    }

    public void setContactList(final List<Contact> contactList) {
        this.contactList = contactList;
    }

    @Override
    public int hashCode() {
        int hash = 0;

        for (final Contact c : this.contactList) {
            hash = Objects.hash(hash, c);
        }

        return hash;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || !(o instanceof ContactList)) {
            return false;
        }

        final ContactList other = (ContactList) o;

        if (this.contactList.size() != other.getContactList().size()) {
            return false;
        }

        for (int i = 0; i < this.contactList.size(); i++) {
            if (!this.contactList.get(i).equals(other.getContactList().get(i))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < this.contactList.size(); i++) {
            sb.append(this.contactList.get(i).toString());
        }

        return sb.toString();
    }
}
