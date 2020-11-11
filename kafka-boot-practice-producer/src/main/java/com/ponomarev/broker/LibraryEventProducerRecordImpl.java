package com.ponomarev.broker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ponomarev.model.LibraryEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@Component
@RequiredArgsConstructor
public class LibraryEventProducerRecordImpl implements EventProducer<LibraryEvent> {

    private final KafkaTemplate<Integer, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void produce(LibraryEvent eventPayload) {
        Integer eventId = eventPayload.getId();
        try {
            String value = objectMapper.writeValueAsString(eventPayload);

            ProducerRecord<Integer, String> producerRecord = new ProducerRecord<>("library-events-2", eventId, value); // topic is not default "library-events" to see diff
            kafkaTemplate.send(producerRecord);
        } catch (JsonProcessingException ex) {
            log.error("Fail during event message payload parsing, payload - {}", eventPayload, ex);
        }  catch (Exception ex) {
            log.error("General fail", ex);
            throw ex;
        }
    }


}
