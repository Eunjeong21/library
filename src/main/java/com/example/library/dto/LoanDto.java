package com.example.library.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanDto {

    @NotNull
    private Long bookId;

    @NotNull
    private Long memberId;
}
