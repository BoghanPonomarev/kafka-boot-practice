package com.ponomarev.broker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ponomarev.model.LibraryEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@Component
@RequiredArgsConstructor
public class SyncLibraryEventProducerImpl implements EventProducer<LibraryEvent> {

    private final KafkaTemplate<Integer, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void produce(LibraryEvent eventPayload) throws ExecutionException, InterruptedException, TimeoutException {
        Integer eventId = eventPayload.getId();
        try {
            String value = objectMapper.writeValueAsString(eventPayload);
            SendResult<Integer, String> sendResult = kafkaTemplate.sendDefault(eventId, value).get(1, TimeUnit.SECONDS);
        } catch (JsonProcessingException ex) {
            log.error("Fail during event message payload parsing, payload - {}", eventPayload, ex);
        } catch (InterruptedException | ExecutionException ex) {
            log.error("Fail during event message payload obtain, payload - {}", eventPayload, ex);
            throw ex;
        } catch (Exception ex) {
            log.error("General fail", ex);
            throw ex;
        }
    }

}
