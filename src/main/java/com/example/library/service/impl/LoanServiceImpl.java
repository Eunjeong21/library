package com.example.library.service.impl;

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
import com.example.library.repository.MemberRepository;
import com.example.library.service.LoanService;
import com.example.library.type.LoanStatus;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LoanServiceImpl implements LoanService {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final LendingDetailRepository lendingDetailRepository;
    private final DelayRepository delayRepository;

    // 반납 메소드의 LocalDate.isAfter() 는 하루를 제외해야 하므로 대출 일수는 14일임
    private static final int RETURN_PERIOD = 15;
    private static final int LIMIT = 5;

    @Override
    public void loanBook(LoanDto request) {
        Member member = memberRepository.findById(request.getMemberId())
            .orElseThrow(IllegalArgumentException::new);

        checkLoanable(member);

        Lending lending = new Lending();
        lending.setLoanDate(LocalDate.now());
        lending.setMember(member);

        LendingDetail lendingDetail = new LendingDetail();
        lending.getLendingDetails().add(lendingDetail);
        lendingDetail.setExpectedReturnDate(LocalDate.now().plusDays(RETURN_PERIOD));

        Book book = bookRepository.findById(request.getBookId())
            .orElseThrow(IllegalArgumentException::new);
        book.setLoanStatus(LoanStatus.ON_LOAN);
        lendingDetail.setReturnStatus(false);
        lendingDetail.setBook(book);
        lendingDetail.setLending(lending);

        List<LendingDetail> findHistories =
            lendingDetailRepository.findFirst5ByMemberIdOrderByIdDesc(request.getMemberId());

        int onLoan = 0;
        for (LendingDetail history : findHistories) {
            if (!history.isReturnStatus()) {
                onLoan++;
            }
            if (onLoan == LIMIT) {
                member.setLoanStatus(LoanStatus.ON_LOAN);
            }
        }
    }

    private static void checkLoanable(Member member) {
        if (member.getLoanStatus().equals(LoanStatus.ON_LOAN)) {
            throw new ExceedingTheLimitException();
        }
        if (member.getLoanStatus().equals(LoanStatus.DELINQUENCY)) {
            throw new DelinquencyException();
        }
    }

    @Override
    public void returnBook(ReturnDto request) {
        Member member = memberRepository.findById(request.getMemberId())
            .orElseThrow(IllegalArgumentException::new);

        LendingDetail lendingDetail = lendingDetailRepository.findById(request.getLendingDetailId())
            .orElseThrow(IllegalArgumentException::new);

        Book book = bookRepository.findById(request.getBookId())
            .orElseThrow(IllegalArgumentException::new);

        if (lendingDetail.getExpectedReturnDate().isBefore(LocalDate.now())) {
            LocalDateTime today = LocalDate.now().atStartOfDay();
            LocalDateTime expectedReturnDate = lendingDetail.getExpectedReturnDate().atStartOfDay();

            int betweenDays =
                (int) Duration.between(expectedReturnDate, today).toDays();

            Delay delay = new Delay();
            delay.setLoanableTransitionDate(LocalDate.now().plusDays(betweenDays));
            delayRepository.save(delay);
            member.setLoanStatus(LoanStatus.DELINQUENCY);
        }

        if (lendingDetail.getExpectedReturnDate().isAfter(LocalDate.now())
        || lendingDetail.getExpectedReturnDate().equals(LocalDate.now())) {
            member.setLoanStatus(LoanStatus.LOANABLE);
        }
        book.setLoanStatus(LoanStatus.LOANABLE);
    }
}
