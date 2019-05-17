package com.example.demo.book;

import java.time.Clock;
import java.time.Instant;
import org.springframework.stereotype.Service;

@Service
public class CurrentTimeGenerator {
    public Instant getCurrentTime() {
        return Clock.systemUTC().instant();
    }
}
