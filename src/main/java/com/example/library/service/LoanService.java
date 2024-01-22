package com.example.library.service;

import com.example.library.dto.LoanDto;
import com.example.library.dto.ReturnDto;

public interface LoanService {

    void loanBook(LoanDto request);

    void returnBook(ReturnDto request);
}
