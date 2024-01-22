package com.example.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class ConfirmPasswordDto {

    @NotNull
    private Long id;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,16}$",
        message = "비밀번호는 영문 대소문자와 숫자를 모두 사용하여 8~16자로 입력해야 합니다.")
    private String inputPassword;
}
