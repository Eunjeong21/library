package com.example.library.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinMemberDto {

    @Pattern(regexp = "^[가-힣]*$",
        message = "올바른 이름을 입력해야 합니다.")
    private String name;

    @Pattern(regexp = "^[a-zA-Z0-9]{6,16}$",
        message = "영문 대소문자와 숫자를 사용하여 6~16자로 입력해야 합니다.")
    private String loginId;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,16}$",
        message = "비밀번호는 영문 대소문자와 숫자를 모두 사용하여 8~16자로 입력해야 합니다.")
    private String password;

    @Pattern(regexp = "^[a-zA-Z0-9]+@[A-Za-z]+\\.[A-Za-z]+$",
        message = "올바른 이메일을 입력해야 합니다.")
    private String email;

    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$",
        message = "올바른 전화번호를 입력해야 합니다.")
    private String phone;

    @Pattern(regexp = "^[0-9a-zA-Zㄱ-ㅎ가-힣 ]*$",
        message = "올바른 주소를 입력해야 합니다.")
    private String address;
}
