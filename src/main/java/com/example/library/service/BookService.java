package com.example.library.service;

import com.example.library.dto.BookDto;

public interface BookService {

    void registerBook(Long bookDetailId, BookDto request);

    void modifyBook(Long bookId, BookDto request);

    void discardBook(Long bookId);
}
