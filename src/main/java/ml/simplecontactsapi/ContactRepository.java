package ml.simplecontactsapi;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class ContactRepository implements PanacheRepository<Contact> {

    public Uni<Contact> findByName(String name) {
        return find("name", name).firstResult();
    }

    public Uni<List<Contact>> findAlive() {
//        return list("status", Status.Alive);
        return null;
    }

    public Uni<Long> deleteStefs() {
        return delete("name", "Stef");
    }
}
