package ml.simplecontactsapi;

import javax.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;

@ApplicationScoped
public class ContactRepository implements PanacheRepository<Contact> {

}
