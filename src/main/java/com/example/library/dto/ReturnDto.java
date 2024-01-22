package com.example.library.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReturnDto {

    @NotNull
    private Long memberId;

    @NotNull
    private Long bookId;

    @NotNull
    private Long lendingDetailId;
}
