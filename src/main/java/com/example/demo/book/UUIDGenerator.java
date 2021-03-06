package com.example.demo.book;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class UUIDGenerator {
    public UUID newUUID() {
        return UUID.randomUUID();
    }

    public Instant getCurrentTime() {
        return Clock.systemUTC().instant();
    }
}
