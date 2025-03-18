package com.getjavajob.training.timashovy.socialnetwork.messaging.producer;

public interface EventProducer {

    void sendEvent(String exchange, String routingKey, Object message);

}
