package com.example.library.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class BookDto {

    @NotNull
    private LocalDate purchaseDate;

    private String remarks;
}
