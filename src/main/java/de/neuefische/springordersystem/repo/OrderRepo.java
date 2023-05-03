package de.neuefische.springordersystem.repo;

import de.neuefische.springordersystem.model.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepo {

    private final Map<Integer, Order> orders = new HashMap<>();

    public void addOrder(Order order) {
        orders.put(order.getId(), order);
    }

    public Order getOrder(int orderId) {
        return orders.get(orderId);
    }

    public List<Order> listOrders() {
        return new ArrayList<>(orders.values());
    }
}
