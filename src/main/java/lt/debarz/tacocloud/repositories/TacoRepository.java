package lt.debarz.tacocloud.repositories;

import lt.debarz.tacocloud.entities.Taco;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends CrudRepository<Taco, Long> {

}
