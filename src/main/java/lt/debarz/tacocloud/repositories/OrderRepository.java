package lt.debarz.tacocloud.repositories;

import lt.debarz.tacocloud.entities.Order;
import lt.debarz.tacocloud.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

//    List<Order> findByZip(String deliveryZip);

//    List<Order> readOrdersByZipAndPlacedAtBetween(String deliveryZip, Date startDate, Date endDate);

//    List<Order> findByDeliveryToAndDeliveryCityAllIgnoresCase(String deliveryTo, String deliveryCity);

//    List<Order> findByCityOrderByName(String city);

    List<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);


    /*@Query("Order o where o.city='Vilnius'")
    List<Order> readOrdersDeliveredToVilnius();*/
}
