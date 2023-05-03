package de.neuefische.springordersystem.service;

import de.neuefische.springordersystem.model.Order;
import de.neuefische.springordersystem.model.Product;
import de.neuefische.springordersystem.repo.OrderRepo;
import de.neuefische.springordersystem.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ShopService {
    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;
    private final GenerateUUID generateUUID;

    public ShopService(ProductRepo productRepo, OrderRepo orderRepo, GenerateUUID generateUUID) {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
        this.generateUUID = generateUUID;
    }

    public Product getProduct(int id) {
        return productRepo.getProduct(id);
    }

    public List<Product> listProducts() {
        return productRepo.listProducts();
    }

    public Order addOrder( List<Integer> productIds) {
        List<Product> products = new ArrayList<>();
        for (int productId : productIds) {
            Product product = productRepo.getProduct(productId);
            products.add(product);
        }

        Order order = new Order( generateUUID.generateUUID(), products);
        return orderRepo.addOrder(order);
    }

    public Order getOrder(String orderId) {
        return orderRepo.getOrder(orderId);
    }

    public List<Order> listOrders() {
        return orderRepo.listOrders();
    }
}
