package com.example.demo.book.configuration;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfiguration {
    @Bean
    public Counter numberOfGets(MeterRegistry meterRegistry) {
        return meterRegistry.counter("number_of_get_books", Collections.emptyList());
    }
}
