package com.ponomarev.service;

import com.ponomarev.broker.EventProducer;
import com.ponomarev.model.LibraryEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LibraryEventServiceImpl implements LibraryEventService {

    @NonNull
    @Qualifier("asyncLibraryEventProducerImpl")
    private final EventProducer<LibraryEvent> libraryEventProducer;

    @Override
    public LibraryEvent publishLibraryEvent(LibraryEvent libraryEvent) throws Exception {
        libraryEventProducer.produce(libraryEvent);
        return libraryEvent;
    }

}
