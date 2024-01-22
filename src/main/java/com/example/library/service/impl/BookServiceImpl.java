package com.example.library.service.impl;

import com.example.library.domain.Book;
import com.example.library.domain.BookDetail;
import com.example.library.dto.BookDto;
import com.example.library.repository.BookDetailRepository;
import com.example.library.repository.BookRepository;
import com.example.library.service.BookService;
import com.example.library.type.LoanStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookDetailRepository bookDetailRepository;

    @Override
    public void registerBook(Long bookDetailId, BookDto request) {
        Book book = new Book();
        book.setPurchaseDate(request.getPurchaseDate());
        book.setRemarks(request.getRemarks());
        book.setLoanStatus(LoanStatus.LOANABLE);

        BookDetail bookDetail = bookDetailRepository.findById(bookDetailId)
            .orElseThrow(IllegalArgumentException::new);
        book.setBookDetail(bookDetail);

        bookRepository.save(book);
    }

    @Override
    public void modifyBook(Long bookId, BookDto request) {
        Book book = bookRepository.findById(bookId)
            .orElseThrow(IllegalArgumentException::new);
        book.setPurchaseDate(request.getPurchaseDate());
        book.setRemarks(request.getRemarks());
    }

    @Override
    public void discardBook(Long bookId) {
        bookRepository.findById(bookId)
            .orElseThrow(IllegalArgumentException::new);

        bookRepository.deleteById(bookId);
    }
}
