package com.example.library.controller.api;

import com.example.library.dto.LoanDto;
import com.example.library.dto.ReturnDto;
import com.example.library.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/loan")
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    public HttpStatus loan(@RequestBody @Valid LoanDto request) {
        loanService.loanBook(request);
        return HttpStatus.OK;
    }

    @PostMapping("/return")
    public HttpStatus returning(@RequestBody @Valid ReturnDto request) {
        loanService.returnBook(request);
        return HttpStatus.OK;
    }
}
