package lt.debarz.tacocloud.repositories;

import lt.debarz.tacocloud.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
}
