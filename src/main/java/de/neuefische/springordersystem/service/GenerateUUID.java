package de.neuefische.springordersystem.service;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@NoArgsConstructor
public class GenerateUUID {

    public String generateUUID(){
        return UUID.randomUUID().toString();
    }

}
