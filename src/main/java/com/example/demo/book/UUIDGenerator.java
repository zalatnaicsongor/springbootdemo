package com.example.demo.book;

import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class UUIDGenerator {
    public UUID newUUID() {
        return UUID.randomUUID();
    }
}
