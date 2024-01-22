package com.example.library.controller.api;

import com.example.library.dto.BookDto;
import com.example.library.service.BookService;
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
@RequestMapping("/api/admin/books")
public class AdminBookController {

    private final BookService bookService;

    @PostMapping
    public HttpStatus register(@RequestBody @Valid Long bookDetailId, BookDto request) {
        bookService.registerBook(bookDetailId, request);
        return HttpStatus.CREATED;
    }

    @PatchMapping
    public HttpStatus modify(@RequestBody @Valid Long bookId, BookDto request) {
        bookService.modifyBook(bookId, request);
        return HttpStatus.OK;
    }

    @DeleteMapping
    public HttpStatus discard(@RequestBody Long bookId) {
        bookService.discardBook(bookId);
        return HttpStatus.OK;
    }
}
