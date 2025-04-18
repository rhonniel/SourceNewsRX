package com.rx.SourceNewsRx.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
public class News {
    private String title;
    private LocalDate date;
    private String author;
}
