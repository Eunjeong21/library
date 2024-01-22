package com.example.library.controller.api;

import com.example.library.dto.BookDetailDto;
import com.example.library.service.BookDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/book/detail")
public class AdminBookDetailController {

    private final BookDetailService bookDetailService;

    @PostMapping
    public HttpStatus register(@RequestBody @Valid BookDetailDto request) {
        bookDetailService.registerBook(request);
        return HttpStatus.CREATED;
    }

    @PatchMapping
    public HttpStatus modify(@RequestBody @Valid Long bookId, BookDetailDto request) {
        bookDetailService.modifyBook(bookId, request);
        return HttpStatus.OK;
    }

    @DeleteMapping
    public HttpStatus discard(@RequestBody Long bookId) {
        bookDetailService.discardBook(bookId);
        return HttpStatus.OK;
    }
}
