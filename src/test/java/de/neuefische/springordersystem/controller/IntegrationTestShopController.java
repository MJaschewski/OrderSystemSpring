package de.neuefische.springordersystem.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
    @DirtiesContext //clears program of method artifacts after test
    //Non void add method
    void whenPostOrder_then200Ok() throws Exception {
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