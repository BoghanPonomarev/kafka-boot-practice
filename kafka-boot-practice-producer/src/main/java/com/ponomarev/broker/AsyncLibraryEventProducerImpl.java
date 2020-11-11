package com.ponomarev.broker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ponomarev.model.LibraryEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
@RequiredArgsConstructor
public class AsyncLibraryEventProducerImpl implements EventProducer<LibraryEvent> {


    private final KafkaTemplate<Integer, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    private LibraryEventCallback libraryEventCallback = new LibraryEventCallback();

    @Override
    public void produce(LibraryEvent eventPayload) {
        Integer eventId = eventPayload.getId();
        try {
            String value = objectMapper.writeValueAsString(eventPayload);
            ListenableFuture<SendResult<Integer, String>> sendResultListenableFuture = kafkaTemplate.sendDefault(eventId, value);
            sendResultListenableFuture.addCallback(libraryEventCallback);
        } catch (JsonProcessingException ex) {
           log.error("Fail during event message payload parsing, payload - {}", eventPayload, ex);
        }
    }

    static class  LibraryEventCallback implements ListenableFutureCallback<SendResult<Integer, String>> {

        @Override
        public void onFailure(Throwable ex) {
            log.error("Message was not delivered", ex);
        }

        @Override
        public void onSuccess(SendResult<Integer, String> result) {
            log.info("Message was sent {}", result.getProducerRecord());
        }

    }

}
