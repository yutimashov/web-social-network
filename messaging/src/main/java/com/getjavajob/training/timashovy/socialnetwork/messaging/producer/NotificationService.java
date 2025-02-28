package com.getjavajob.training.timashovy.socialnetwork.messaging.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Service;

@Service
public class NotificationService implements EventProducer {

    private final RabbitTemplate rabbitTemplate;

    public NotificationService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    }

    @Override
    public void sendEvent(Object message) {
        rabbitTemplate.convertAndSend("gjj-exchange", "notifications.birthday", message);
    }

}
