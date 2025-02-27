package com.getjavajob.training.timashovy.socialnetwork.service.rabbitmq;

public interface EventProducer {

    void sendEvent(Object message);

}
