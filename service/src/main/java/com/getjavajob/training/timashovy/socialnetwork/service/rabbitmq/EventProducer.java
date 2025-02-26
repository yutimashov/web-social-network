package com.getjavajob.training.timashovy.socialnetwork.service.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {

    @Value("${rabbit.mq.exchange.name}")
    private String exchangeName;

    @Value("${rabbit.mq.routing.key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public EventProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendEvent(String message) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
    }

}
