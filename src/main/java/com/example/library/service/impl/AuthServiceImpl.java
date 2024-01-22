package com.example.library.service.impl;

import com.example.library.dto.LoginDto;
import com.example.library.repository.MemberRepository;
import com.example.library.security.token.TokenProvider;
import com.example.library.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final TokenProvider tokenProvider;

    @Override
    public void login(LoginDto request) {
    }

    @Override
    public void logout() {
        // 나중에 redis 이용한 blacklist 추가 구현
        // 현재는 authController 에서 토큰 임의 만료
    }

}
