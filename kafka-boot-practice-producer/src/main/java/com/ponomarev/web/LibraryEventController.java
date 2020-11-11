package com.ponomarev.web;

import com.ponomarev.model.LibraryEvent;
import com.ponomarev.service.LibraryEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
public class LibraryEventController {

    private final LibraryEventService libraryEventService;

    @PostMapping(value = "/v1/library-event", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<LibraryEvent> postLibraryEvent(@RequestBody LibraryEvent libraryEvent) throws Exception {
        LibraryEvent resultLibraryEvent = libraryEventService.publishLibraryEvent(libraryEvent);
        return  ResponseEntity.status(CREATED).body(resultLibraryEvent);
    }
}
