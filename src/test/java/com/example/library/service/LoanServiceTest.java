package com.example.library.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.library.domain.Book;
import com.example.library.domain.Delay;
import com.example.library.domain.Lending;
import com.example.library.domain.LendingDetail;
import com.example.library.domain.Member;
import com.example.library.dto.LoanDto;
import com.example.library.dto.ReturnDto;
import com.example.library.exception.impl.Loan.DelinquencyException;
import com.example.library.exception.impl.Loan.ExceedingTheLimitException;
import com.example.library.repository.BookRepository;
import com.example.library.repository.DelayRepository;
import com.example.library.repository.LendingDetailRepository;
import com.example.library.repository.LendingRepository;
import com.example.library.repository.MemberRepository;
import com.example.library.type.LoanStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class LoanServiceTest {

    @Autowired
    LoanService loanService;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    LendingRepository lendingRepository;
    @Autowired
    LendingDetailRepository lendingDetailRepository;
    @Autowired
    DelayRepository delayRepository;

    @DisplayName("대출 성공")
    @Test
    void successLoan() {
        //given
        Book book = new Book();
        book.setPurchaseDate(LocalDate.now());
        bookRepository.save(book);

        Member member = new Member();
        member.setName("테스트");
        member.setLoginId("test111");
        member.setPassword("password111");
        member.setEmail("testEmail111@test.com");
        member.setPhone("010-1111-1111");
        member.setAddress("테스트용 주소입니다");
        member.setLoanStatus(LoanStatus.LOANABLE);
        memberRepository.save(member);

        LoanDto loan = new LoanDto();
        loan.setBookId(book.getId());
        loan.setMemberId(member.getId());

        //when
        loanService.loanBook(loan);

        //then
        Optional<Lending> lending = lendingRepository.findById(1L);
        Optional<LendingDetail> lendingDetail = lendingDetailRepository.findById(1L);
        List<LendingDetail> lendingDetails = lendingDetailRepository.findAll();

        assertThat(lending.get().getMember()).isEqualTo(member);
        assertThat(lendingDetail.get().getExpectedReturnDate()).isEqualTo(LocalDate.now().plusDays(15));
        assertThat(lendingDetail.get().isReturnStatus()).isFalse();
        assertThat(lendingDetails.size()).isEqualTo(1);
        assertThat(book.getLoanStatus()).isEqualTo(LoanStatus.ON_LOAN);
    }

    @DisplayName("대출 실패 - 대출 권수 초과")
    @Test
    void failedLoan_limitedMember() {
        //given
        Book book = new Book();
        book.setId(1L);
        book.setPurchaseDate(LocalDate.now());
        bookRepository.save(book);

        Member member = new Member();
        member.setId(1L);
        member.setName("테스트");
        member.setLoginId("test111");
        member.setPassword("password111");
        member.setEmail("testEmail111@test.com");
        member.setPhone("010-1111-1111");
        member.setAddress("테스트용 주소입니다");
        member.setLoanStatus(LoanStatus.ON_LOAN);
        memberRepository.save(member);

        LendingDetail lendingDetail1 = new LendingDetail();
        lendingDetail1.setExpectedReturnDate(LocalDate.now().plusDays(15));
        lendingDetail1.setReturnStatus(true);
        lendingDetail1.setMember(member);
        lendingDetailRepository.save(lendingDetail1);

        LendingDetail lendingDetail2 = new LendingDetail();
        lendingDetail2.setExpectedReturnDate(LocalDate.now().plusDays(15));
        lendingDetail2.setReturnStatus(true);
        lendingDetail2.setMember(member);
        lendingDetailRepository.save(lendingDetail2);

        LendingDetail lendingDetail3 = new LendingDetail();
        lendingDetail3.setExpectedReturnDate(LocalDate.now().plusDays(15));
        lendingDetail3.setReturnStatus(true);
        lendingDetail3.setMember(member);
        lendingDetailRepository.save(lendingDetail3);

        LendingDetail lendingDetail4 = new LendingDetail();
        lendingDetail4.setExpectedReturnDate(LocalDate.now().plusDays(15));
        lendingDetail4.setReturnStatus(true);
        lendingDetail4.setMember(member);
        lendingDetailRepository.save(lendingDetail4);

        LendingDetail lendingDetail5 = new LendingDetail();
        lendingDetail5.setExpectedReturnDate(LocalDate.now().plusDays(15));
        lendingDetail5.setReturnStatus(true);
        lendingDetail5.setMember(member);
        lendingDetailRepository.save(lendingDetail5);

        LoanDto loan = new LoanDto();
        loan.setBookId(book.getId());
        loan.setMemberId(member.getId());

        //when
        //then
        assertThatThrownBy(() -> {
            loanService.loanBook(loan);
        }).isInstanceOf(ExceedingTheLimitException.class);
    }

    @DisplayName("대출 실패 - 연체중인 회원")
    @Test
    void failedLoan_delinquentMember() {
        //given
        Book book = new Book();
        book.setId(1L);
        book.setPurchaseDate(LocalDate.now());
        bookRepository.save(book);

        Member member = new Member();
        member.setId(1L);
        member.setName("테스트");
        member.setLoginId("test111");
        member.setPassword("password111");
        member.setEmail("testEmail111@test.com");
        member.setPhone("010-1111-1111");
        member.setAddress("테스트용 주소입니다");
        member.setLoanStatus(LoanStatus.DELINQUENCY);
        memberRepository.save(member);

        LoanDto loan = new LoanDto();
        loan.setBookId(book.getId());
        loan.setMemberId(member.getId());

        //when
        //then
        assertThatThrownBy(() -> {
            loanService.loanBook(loan);
        }).isInstanceOf(DelinquencyException.class);
    }

    @DisplayName("반납 성공")
    @Test
    void successReturn() {
        //given
        Member member = new Member();
        member.setId(1L);
        member.setName("테스트");
        member.setLoginId("test111");
        member.setPassword("password111");
        member.setEmail("testEmail111@test.com");
        member.setPhone("010-1111-1111");
        member.setAddress("테스트용 주소입니다");
        member.setLoanStatus(LoanStatus.ON_LOAN);
        memberRepository.save(member);

        Book book = new Book();
        book.setId(1L);
        book.setPurchaseDate(LocalDate.now());
        book.setLoanStatus(LoanStatus.ON_LOAN);
        bookRepository.save(book);

        LendingDetail lendingDetail = new LendingDetail();
        lendingDetail.setExpectedReturnDate(LocalDate.now().plusDays(15));
        lendingDetail.setReturnStatus(true);
        lendingDetail.setMember(member);
        lendingDetailRepository.save(lendingDetail);

        ReturnDto returnBook = new ReturnDto();
        returnBook.setBookId(book.getId());
        returnBook.setMemberId(member.getId());
        returnBook.setLendingDetailId(lendingDetail.getId());

        //when
        loanService.returnBook(returnBook);

        //then
        assertThat(member.getLoanStatus()).isEqualTo(LoanStatus.LOANABLE);
        assertThat(book.getLoanStatus()).isEqualTo(LoanStatus.LOANABLE);
    }

    @DisplayName("반납 성공 - 연체된 회원")
    @Test
    void successReturn_() {
        //given
        Member member = new Member();
        member.setId(1L);
        member.setName("테스트");
        member.setLoginId("test111");
        member.setPassword("password111");
        member.setEmail("testEmail111@test.com");
        member.setPhone("010-1111-1111");
        member.setAddress("테스트용 주소입니다");
        member.setLoanStatus(LoanStatus.LOANABLE);
        memberRepository.save(member);

        Book book = new Book();
        book.setId(1L);
        book.setPurchaseDate(LocalDate.now());
        book.setLoanStatus(LoanStatus.ON_LOAN);
        bookRepository.save(book);

        LendingDetail lendingDetail = new LendingDetail();
        lendingDetail.setExpectedReturnDate(LocalDate.now().minusDays(15));
        lendingDetail.setReturnStatus(true);
        lendingDetail.setMember(member);
        lendingDetailRepository.save(lendingDetail);

        ReturnDto returnBook = new ReturnDto();
        returnBook.setBookId(book.getId());
        returnBook.setMemberId(member.getId());
        returnBook.setLendingDetailId(lendingDetail.getId());

        //when
        loanService.returnBook(returnBook);

        //then
        assertThat(member.getLoanStatus()).isEqualTo(LoanStatus.DELINQUENCY);
        assertThat(book.getLoanStatus()).isEqualTo(LoanStatus.LOANABLE);

        Optional<Delay> delay = delayRepository.findById(1L);
        assertThat(delay.get().getLoanableTransitionDate()).isEqualTo(LocalDate.now().plusDays(15));
    }

//    @DisplayName("반납 실패 - 예상 경우 없음")
//    @Test
//    void failedReturn() {
//        //given
//        //when
//        //then
//    }
}
