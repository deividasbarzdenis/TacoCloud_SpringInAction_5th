package lt.debarz.tacocloud.repositories;

import lt.debarz.tacocloud.entities.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {

}
