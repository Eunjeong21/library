package com.example.library.service.impl;

import static com.example.library.domain.QMember.member;

import com.example.library.domain.Member;
import com.example.library.dto.ConfirmPasswordDto;
import com.example.library.dto.JoinMemberDto;
import com.example.library.dto.ModifyMemberDto;
import com.example.library.dto.SearchMemberDto;
import com.example.library.exception.impl.Member.AlreadyWithdrawalException;
import com.example.library.exception.impl.Member.LoginIdDuplicatedException;
import com.example.library.exception.impl.Member.WrongConfirmPasswordException;
import com.example.library.repository.MemberRepository;
import com.example.library.service.MemberService;
import com.example.library.type.LoanStatus;
import com.example.library.type.Permission;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final JPAQueryFactory query;

    @Override
    public void joinMember(JoinMemberDto request) {
        checkDuplicatedLoginId(request);

        Member member = new Member();
        String encodedPassword = encodePassword(request.getPassword());

        member.setName(request.getName());
        member.setLoginId(request.getLoginId());
        member.setPassword(encodedPassword);
        member.setEmail(request.getEmail());
        member.setPhone(request.getPhone());
        member.setAddress(request.getAddress());
        member.setLoanStatus(LoanStatus.LOANABLE);
        member.setWithdrawalStatus(false);
        member.setPermission(Permission.ROLE_USER);

        memberRepository.save(member);
    }

    @Override
    public void modifyMember(ModifyMemberDto request) {
        Member member = memberRepository.findById(request.getId())
            .orElseThrow(RuntimeException::new);

        String encodedPassword = encodePassword(request.getPassword());

        member.setPassword(encodedPassword);
        member.setEmail(request.getEmail());
        member.setPhone(request.getPhone());
        member.setAddress(request.getAddress());
    }

    @Override
    public void withdrawalMember(Long id) {
        Member member = memberRepository.findById(id)
            .orElseThrow(IllegalArgumentException::new);

        if (member.isWithdrawalStatus()) {
            throw new AlreadyWithdrawalException();
        }

        memberRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Member> searchKeywordMember(SearchMemberDto keyword) {
        String name = keyword.getName();
        String loginId = keyword.getLoginId();
        String email = keyword.getEmail();

        return query
            .select(member)
            .from(member)
            .where(equalName(name), likeLoginId(loginId), likeEmail(email))
            .fetch();
    }

    private BooleanExpression equalName(String name) {
        if (StringUtils.hasText(name)) {
            return member.name.eq(name);
        }
        return null;
    }

    private BooleanExpression likeLoginId(String loginId) {
        if (StringUtils.hasText(loginId)) {
            return member.name.like("%" + loginId + "%");
        }
        return null;
    }

    private BooleanExpression likeEmail(String email) {
        if (StringUtils.hasText(email)) {
            return member.name.like("%" + email + "%");
        }
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Member> searchAllMember() {
        return memberRepository.findAll();
    }

    @Override
    public void confirmPassword(ConfirmPasswordDto request) {
        Member member = memberRepository.findById(request.getId())
            .orElseThrow(RuntimeException::new);

        String password = member.getPassword();
        boolean matches = encoder.matches(request.getInputPassword(), password);

        if (!matches) {
            throw new WrongConfirmPasswordException();
        }
    }

    private String encodePassword(String newPassword) {
        return encoder.encode(newPassword);
    }

    private void checkDuplicatedLoginId(JoinMemberDto request) {
        memberRepository.findByLoginId(request.getLoginId())
            .ifPresent(member -> {
                throw new LoginIdDuplicatedException();
            });
    }
}
