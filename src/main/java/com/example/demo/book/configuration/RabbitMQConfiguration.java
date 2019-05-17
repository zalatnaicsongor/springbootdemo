package com.example.demo.book.configuration;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    public RabbitMQConfiguration(RabbitTemplate rabbitTemplate) {
        rabbitTemplate.setChannelTransacted(true);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    }
}
