package com.example.library.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMemberDto {

    private String name;
    private String loginId;
    private String email;
    private String phone;
    private String address;
    private String loanStatus;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private boolean withdrawalStatus;
}
