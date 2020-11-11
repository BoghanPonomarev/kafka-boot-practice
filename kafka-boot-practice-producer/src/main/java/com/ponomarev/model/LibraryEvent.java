package com.ponomarev.model;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class LibraryEvent {

    private Integer id;
    private Book book;

}
