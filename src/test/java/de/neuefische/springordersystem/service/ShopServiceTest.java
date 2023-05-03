package de.neuefische.springordersystem.service;

import de.neuefische.springordersystem.model.Order;
import de.neuefische.springordersystem.model.Product;
import de.neuefische.springordersystem.repo.OrderRepo;
import de.neuefische.springordersystem.repo.ProductRepo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShopServiceTest {
    ProductRepo productRepo = mock(ProductRepo.class);
    OrderRepo orderRepo = mock(OrderRepo.class);
    GenerateUUID generateUUID = mock(GenerateUUID.class);
    ShopService service = new ShopService(productRepo,orderRepo,generateUUID);

    @Test
    void getProduct_should_return_right_product(){
        //Given
        int productId = 2;
        String productName;
        Product expected = new Product(2, "Banane");

        //When
        when(productRepo.getProduct(productId)).thenReturn(expected);
        Product actual = service.getProduct(productId);

        //Then
        assertEquals(expected,actual);
        verify(productRepo).getProduct(productId);
    }

    @Test
    void listProducts_should_return_right_list(){
        //Given
        Map<Integer, Product> products = Map.of(
                1, new Product(1, "Apfel"),
                2, new Product(2, "Banane"),
                3, new Product(3, "Zitrone"),
                4, new Product(4, "Mandarine")
        );
        List<Product> expected = new ArrayList<>(products.values());

        //When
        when(productRepo.listProducts()).thenReturn(expected);
        List<Product> actual = service.listProducts();

        //Then
        assertEquals(expected,actual);
        verify(productRepo).listProducts();

    }

    @Test
    void getOrder_should_return_right_order(){
        //Given
        List<Product> productList = new ArrayList<>();
        when(generateUUID.generateUUID()).thenReturn("1");

        Order expected = new Order(generateUUID.generateUUID(),productList);

        //When
        when(orderRepo.getOrder(expected.getId())).thenReturn(expected);
        Order actual = service.getOrder(expected.getId());

        //Then
        assertEquals(expected,actual);
        verify(orderRepo).getOrder(expected.getId());
    }

    @Test
    void listOrders_returns_listOfOrders(){
        //Given
        List<Order> expected = new ArrayList<>();

        //When
        when(orderRepo.listOrders()).thenReturn(expected);
        List<Order> actual = service.listOrders();

        //Then
        assertEquals(expected,actual);
        verify(orderRepo).listOrders();

    }
}