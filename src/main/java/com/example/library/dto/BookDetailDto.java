package com.example.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class BookDetailDto {

    @NotBlank
    private String classificationCode;

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    private String publisher;

    @NotNull
    private LocalDate publishedDate;

    @NotBlank
    private String country;

    @NotNull
    private boolean newBookStatus;
}
