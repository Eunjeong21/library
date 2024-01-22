package com.example.library.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBookDetailDto {

    private String classificationCode;
    private String title;
    private String author;
    private String publisher;
    private LocalDate publishedDate;
    private String country;
    private boolean newBookStatus;
}
