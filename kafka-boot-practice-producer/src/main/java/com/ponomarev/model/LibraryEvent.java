package com.ponomarev.model;

import lombok.*;
import lombok.experimental.Accessors;

@Builder
@Accessors
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class LibraryEvent {

    private Integer id;
    private Book book;

}
