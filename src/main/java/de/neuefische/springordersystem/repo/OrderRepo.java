package de.neuefische.springordersystem.repo;

import de.neuefische.springordersystem.model.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepo {

    private final Map<String, Order> orders = new HashMap<>();

    public Order addOrder(Order order) {
        orders.put(order.getId(), order);
        return order;
    }

    public Order getOrder(String orderId) {
        return orders.get(orderId);
    }

    public List<Order> listOrders() {
        return new ArrayList<>(orders.values());
    }
}
