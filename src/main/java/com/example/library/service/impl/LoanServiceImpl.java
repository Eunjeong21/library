package com.example.library.service.impl;

import com.example.library.domain.Book;
import com.example.library.domain.Delay;
import com.example.library.domain.Lending;
import com.example.library.domain.LendingDetail;
import com.example.library.domain.Member;
import com.example.library.dto.LoanDto;
import com.example.library.dto.ReturnDto;
import com.example.library.repository.BookRepository;
import com.example.library.repository.LendingDetailRepository;
import com.example.library.repository.MemberRepository;
import com.example.library.service.LoanService;
import com.example.library.type.LoanStatus;
import java.time.Duration;
import java.time.LocalDate;
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

    // 반납 메소드의 LocalDate.isAfter() 는 하루를 제외해야 하므로 대출 일수는 14일임
    private static final int RETURN_PERIOD = 15;
    private static final int LIMIT = 5;

    @Override
    public void loanBook(LoanDto request) {
        Member member = memberRepository.findById(request.getMemberId())
            .orElseThrow(RuntimeException::new);

        checkLoanable(member);

        Lending lending = new Lending();
        lending.setLoanDate(LocalDate.now());
        lending.setMember(member);

        LendingDetail lendingDetail = new LendingDetail();
        lending.getLendingDetails().add(lendingDetail);
        lendingDetail.setExpectedReturnDate(LocalDate.now().plusDays(RETURN_PERIOD));

        Book book = bookRepository.findById(request.getBookId())
            .orElseThrow(RuntimeException::new);
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
            throw new RuntimeException("책은 다섯 권까지 빌릴 수 있습니다.");
        }
        if (member.getLoanStatus().equals(LoanStatus.DELINQUENCY)) {
            throw new RuntimeException("연체 제재 기간 동안에는 대출할 수 없습니다.");
        }
    }

    @Override
    public void returnBook(ReturnDto request) {
        Member member = memberRepository.findById(request.getMemberId())
            .orElseThrow(RuntimeException::new);

        LendingDetail lendingDetail = lendingDetailRepository.findById(request.getLendingDetailId())
            .orElseThrow(RuntimeException::new);

        Book book = bookRepository.findById(request.getBookId())
            .orElseThrow(RuntimeException::new);

        if (lendingDetail.getExpectedReturnDate().isBefore(LocalDate.now())) {
            int betweenDays =
                (int) Duration.between(lendingDetail.getExpectedReturnDate(), LocalDate.now()).toDays();
            Delay delay = new Delay();
            delay.setLoanableTransitionDate(LocalDate.now().plusDays(betweenDays));
            member.setLoanStatus(LoanStatus.DELINQUENCY);
        }

        if (lendingDetail.getExpectedReturnDate().isAfter(LocalDate.now())) {
            member.setLoanStatus(LoanStatus.LOANABLE);
        }
        book.setLoanStatus(LoanStatus.LOANABLE);
    }
    
}
