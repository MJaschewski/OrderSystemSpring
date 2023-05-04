package de.neuefische.springordersystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.neuefische.springordersystem.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class IntegrationTestShopController {

    @Autowired
    MockMvc mockMcv;

    @Test
    void getAllOrders_thenReturnEmptyOrderList() throws Exception {
        mockMcv.perform(MockMvcRequestBuilders.get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DirtiesContext
    void getProducts_returnsListOfProducts() throws Exception {
        mockMcv.perform(MockMvcRequestBuilders.get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().json("""   
                                                    [
                                                        {"id":1,"name":"Apfel"},
                                                        {"id":2,"name":"Banane"},
                                                        {"id":3,"name":"Zitrone"},
                                                        {"id":4,"name":"Mandarine"}
                                                    ]
                                               """));
    }

    @Test
    @DirtiesContext
    void getProduct_wrongIdRThrowsNoSuchElementExceptionWithCorrectMessage(){
        try{
        mockMcv.perform(MockMvcRequestBuilders.get("/api/products/5"));
                fail();}
        catch (Exception e){
            String expected = "Request processing failed: java.util.NoSuchElementException: No product with id 5 found in this product repo.";
            String actual = e.getMessage();
            assertEquals(expected,actual);
            }
    }

    @Test
    @DirtiesContext
    void getProduct_rightIdReturnsRightProduct() throws Exception {
        mockMcv.perform(MockMvcRequestBuilders.get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("""      
                                                {
                                                    "id" : 1,
                                                    "name" : "Apfel"         
                                                }
                                            """));
    }

    @Test
    @DirtiesContext
    void whenGetOrder_thenReturn200Ok_and_RightOrder() throws Exception{
        //Add order to the empty orderList
        MvcResult response = mockMcv.perform(MockMvcRequestBuilders.post("/api/orders/add") //PathVariable defined in url. Example: /api/orders/2
                        .contentType(MediaType.APPLICATION_JSON) // alt writing: "application/json"
                        .content("[1]"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                            {
                                                "products" : [
                                                    {
                                                        "id" : 1,
                                                        "name" : "Apfel"         
                                                    }
                                                ]
                                            }
                                        """))
                .andExpect(jsonPath("$.id").isNotEmpty()).andReturn();

        String content = response.getResponse().getContentAsString();
        Order order = new ObjectMapper().readValue(content, Order.class);
        String orderId = order.getId();

        mockMcv.perform(MockMvcRequestBuilders.get("/api/orders/" + orderId))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                                        {
                                                            "products":[{"id":1,"name":"Apfel"}]
                                                        }
                                                    """))
                .andExpect(jsonPath("$.id").value(orderId));
    }

    @Test
    @DirtiesContext //clears program of method artifacts after test
    //Non void add method
    void whenPostOrder_thenReturnAddedOrder() throws Exception {
        mockMcv.perform(MockMvcRequestBuilders.post("/api/orders/add") //PathVariable defined in url. Example: /api/orders/2
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[1]"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                                {
                                                    "products" : [
                                                        {
                                                            "id" : 1,
                                                            "name" : "Apfel"         
                                                        }
                                                    ]
                                                }
                                            """))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }
    /*
    //Test of void method
    @Test
    void whenPostOrder_then200Ok() throws Exception {
        mockMcv.perform(MockMvcRequestBuilders.post("/api/orders/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[1]"))
                .andExpect(status().isOk());

        mockMcv.perform(MockMvcRequestBuilders.get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [
                            {
                                "products" : [
                                    {
                                        "id" : 1,
                                        "name" : "Apfel"
                                    }
                                ]
                            }
                        ]
                        """))
                .andExpect(jsonPath("$[0].id").isNotEmpty());
    }
    */
}