package com.example.library.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {

    @NotNull
    private LocalDate purchaseDate;

    private String remarks;
}
