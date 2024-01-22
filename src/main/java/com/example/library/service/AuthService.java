package com.example.library.service;

import com.example.library.dto.LoginDto;

public interface AuthService {

    void login(LoginDto request);

    void logout();

}
