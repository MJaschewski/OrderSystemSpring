package de.neuefische.springordersystem.repo;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepoTest {

    @Test
    void getProduct_throws_exception_id_notfound_with_message() {
        //Given
        int wrongID = 6;
        ProductRepo productRepo = new ProductRepo();

        //When
        String expected = "No product with id 6 found in this product repo.";
        String  actual = "";
        try {
            productRepo.getProduct(wrongID);
            fail();
        }catch (NoSuchElementException e){
             actual = e.getMessage();
        }

        //Then
        assertEquals(expected,actual);
    }
}