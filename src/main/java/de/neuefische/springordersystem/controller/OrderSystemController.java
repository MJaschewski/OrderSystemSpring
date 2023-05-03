package de.neuefische.springordersystem.controller;

import de.neuefische.springordersystem.model.Order;
import de.neuefische.springordersystem.model.Product;
import de.neuefische.springordersystem.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor

public class OrderSystemController {
    private final ShopService shopService;

    @GetMapping("products")
    public List<Product> getProducts(){
        return shopService.listProducts();
    }

    @GetMapping("products/{id}")
    public Product getProduct(@PathVariable int id){
        return shopService.getProduct(id);
    }

    @GetMapping("orders")
    public List<Order> getOrders(){
        return shopService.listOrders();
    }

    @GetMapping("orders/{id}")
    public Order getOrder(@PathVariable int id){
        return shopService.getOrder(id);
    }

    @PostMapping("orders/{id}")
    public Order postOrder(@PathVariable int id, @RequestBody List<Integer> orderList){
        shopService.addOrder(id, orderList);
        return shopService.getOrder(id);
    }
}
