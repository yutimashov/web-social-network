package com.getjavajob.training.timashovy.socialnetwork.service.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class AccountWallPostNotification implements EventProducer {

    private final RabbitTemplate rabbitTemplate;

    public AccountWallPostNotification(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendEvent(String message) {
        rabbitTemplate.convertAndSend("social-network-exchange", "social.network.events", message);
    }

}
