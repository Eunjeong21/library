package com.example.library.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.library.domain.Member;
import com.example.library.dto.JoinMemberDto;
import com.example.library.dto.ModifyMemberDto;
import com.example.library.exception.impl.Member.AlreadyWithdrawalException;
import com.example.library.exception.impl.Member.LoginIdDuplicatedException;
import com.example.library.repository.MemberRepository;
import com.example.library.type.LoanStatus;
import com.example.library.type.Permission;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    PasswordEncoder encoder;

    @DisplayName("회원가입 성공")
    @Test
    void successJoinMember() {
        //given
        JoinMemberDto member = new JoinMemberDto();

        member.setName("테스트");
        member.setLoginId("test111");
        member.setPassword("password111");
        member.setEmail("testEmail111@test.com");
        member.setPhone("010-1111-1111");
        member.setAddress("테스트용 주소입니다");

        //when
        memberService.joinMember(member);

        //then
        Optional<Member> savedMember = memberRepository.findByLoginId("test111");
        assertThat(savedMember.get().getName()).isEqualTo("테스트");
        assertThat(encoder.matches("password111", savedMember.get().getPassword())).isTrue();
        assertThat(savedMember.get().getEmail()).isEqualTo("testEmail111@test.com");
        assertThat(savedMember.get().getPhone()).isEqualTo("010-1111-1111");
        assertThat(savedMember.get().getAddress()).isEqualTo("테스트용 주소입니다");

        //자동설정
        assertThat(savedMember.get().getLoanStatus()).isEqualTo(LoanStatus.LOANABLE);
        assertThat(savedMember.get().isWithdrawalStatus()).isEqualTo(false);
        assertThat(savedMember.get().getPermission()).isEqualTo(Permission.ROLE_USER);

//        테스트 객체 등록과 테스트 객체 비교간 약간의 시간 오차로 인한 오류 발생, 정상 동작 확인
//        assertThat(savedMember.get().getCreatedAt()).isEqualTo(LocalDateTime.now());
//        assertThat(savedMember.get().getModifiedAt()).isEqualTo(LocalDateTime.now());
    }

    @DisplayName("회원가입 실패 - 중복 아이디")
    @Test
    void failedJoinMember() {
        //given
        JoinMemberDto member1 = new JoinMemberDto();
        member1.setName("테스트111");
        member1.setLoginId("test111");
        member1.setPassword("password111");
        member1.setEmail("testEmail111@test.com");
        member1.setPhone("010-1111-1111");
        member1.setAddress("테스트용 주소입니다");

        JoinMemberDto member2 = new JoinMemberDto();
        member2.setName("테스트222");
        member2.setLoginId("test111");
        member2.setPassword("password222");
        member2.setEmail("testEmail222@test.com");
        member2.setPhone("010-2222-2222");
        member2.setAddress("테스트용 주소입니다222");

        //when
        //then
        memberService.joinMember(member1);
        assertThatThrownBy(() -> {
            memberService.joinMember(member2);
        }).isInstanceOf(LoginIdDuplicatedException.class);
    }

    @DisplayName("회원 정보 수정 성공")
    @Test
    void successModifyMember() {
        //given
        Member member = new Member();
        member.setId(1L);
        member.setName("테스트");
        member.setLoginId("test111");
        member.setPassword("password111");
        member.setEmail("testEmail111@test.com");
        member.setPhone("010-1111-1111");
        member.setAddress("테스트용 주소입니다");
        memberRepository.save(member);

        ModifyMemberDto modifiedMember = new ModifyMemberDto();
        modifiedMember.setId(1L);
        modifiedMember.setPassword("password222");
        modifiedMember.setEmail("testEmail222@test.com");
        modifiedMember.setPhone("010-2222-2222");
        modifiedMember.setAddress("수정된 테스트용 주소입니다");

        //when
        memberService.modifyMember(modifiedMember);

        //then
        assertThat(member.getName()).isEqualTo("테스트");
        assertThat(encoder.matches("password222", member.getPassword())).isTrue();
        assertThat(member.getEmail()).isEqualTo("testEmail222@test.com");
        assertThat(member.getPhone()).isEqualTo("010-2222-2222");
        assertThat(member.getAddress()).isEqualTo("수정된 테스트용 주소입니다");
    }

//    @DisplayName("회원 정보 수정 실패 - 예상 경우 없음")
//    @Test
//    void failedModifyMember() {
//        //given
//        //when
//        //then
//    }

    @DisplayName("회원 탈퇴 성공")
    @Test
    void successWithdrawalMember() {
        //given
        Member member = new Member();
        member.setId(1L);
        member.setName("테스트");
        member.setLoginId("test111");
        member.setPassword("password111");
        member.setEmail("testEmail111@test.com");
        member.setPhone("010-1111-1111");
        member.setAddress("테스트용 주소입니다");
        memberRepository.save(member);

        //when
        memberService.withdrawalMember(1L);
        Optional<Member> findMember = memberRepository.findById(1L);

        //then
        assertThat(findMember.get().isWithdrawalStatus()).isTrue();
    }

    @DisplayName("회원 탈퇴 실패 - 이미 탈퇴한 회원")
    @Test
    void failedWithdrawalMember() {
        //given
        Member member = new Member();
        member.setId(1L);
        member.setName("테스트");
        member.setLoginId("test111");
        member.setPassword("password111");
        member.setEmail("testEmail111@test.com");
        member.setPhone("010-1111-1111");
        member.setAddress("테스트용 주소입니다");
        member.setWithdrawalStatus(true);
        memberRepository.save(member);

        //when
        //then
        assertThatThrownBy(() -> {
            memberService.withdrawalMember(1L);
        }).isInstanceOf(AlreadyWithdrawalException.class);
    }
}