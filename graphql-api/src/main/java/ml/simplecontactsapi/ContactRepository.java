package ml.simplecontactsapi;

import java.util.List;
import java.util.function.Predicate;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class ContactRepository implements PanacheRepository<Contact> {
    public Uni<List<Contact>> findByName(String name) {
        return this.findAll().stream().filter(new Predicate<Contact>() {
            @Override
            public boolean test(Contact c) {
                if (c.getFirstName() != null && c.getFirstName().equalsIgnoreCase(name)) {
                    return true;
                }

                else if (c.getMiddleName() != null && c.getMiddleName().equalsIgnoreCase(name)) {
                    return true;
                }

                else if (c.getLastName() != null && c.getLastName().equalsIgnoreCase(name)) {
                    return true;
                }

                else {
                    return false;
                }
            }
        }).collect().asList();
    }

    public Uni<List<Contact>> searchByName(String name) {
        return this.findAll().stream().filter(new Predicate<Contact>() {
            @Override
            public boolean test(Contact c) {
                return c.getFullName().toLowerCase().contains(name.toLowerCase());
            }
        }).collect().asList();
    }

    public Uni<List<Contact>> findByEmail(String email) {
        return this.find("email", email).list();
    }

    public Uni<List<Contact>> searchByEmail(String email) {
        return this.findAll().stream().filter(new Predicate<Contact>() {
            @Override
            public boolean test(Contact c) {
                return c.getEmail().toLowerCase().contains(email.toLowerCase());
            }
        }).collect().asList();
    }
}
