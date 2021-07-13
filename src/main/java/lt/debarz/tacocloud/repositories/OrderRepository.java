package lt.debarz.tacocloud.repositories;

import lt.debarz.tacocloud.entities.Order;

public interface OrderRepository {

    Order save(Order order);
}
