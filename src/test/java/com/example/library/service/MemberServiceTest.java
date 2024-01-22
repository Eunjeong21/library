package com.example.library.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Test
    void successJoinMember() {
        //given
        //when
        //then
    }

    @Test
    void failedJoinMember() {
        //given
        //when
        //then
    }

    @Test
    void successModifyMember() {
        //given
        //when
        //then
    }

    @Test
    void failedModifyMember() {
        //given
        //when
        //then
    }

    @Test
    void successWithdrawalMember() {
        //given
        //when
        //then
    }

    @Test
    @DisplayName("탈퇴 실패 - 이미 탈퇴한 회원")
    void failedWithdrawalMember() {
        //given
        //when
        //then
    }

}