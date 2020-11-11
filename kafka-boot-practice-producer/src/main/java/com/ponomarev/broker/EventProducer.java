package com.ponomarev.broker;

public interface EventProducer<T> {

    void produce(T eventPayload) throws Exception;

}
