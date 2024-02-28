package com.example.library.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.library.domain.Book;
import com.example.library.domain.BookDetail;
import com.example.library.dto.BookDto;
import com.example.library.repository.BookDetailRepository;
import com.example.library.repository.BookRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class BookServiceTest {

    @Autowired
    BookDetailRepository bookDetailRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    BookService bookService;

    @DisplayName("도서 관리 등록 성공")
    @Test
    void successRegisterBook() {
        //given
        BookDetail bookDetail = new BookDetail();
        bookDetail.setId(1L);
        bookDetail.setClassificationCode("분류기호");
        bookDetail.setTitle("제목");
        bookDetail.setAuthor("작가명");
        bookDetail.setPublisher("출판사");
        bookDetail.setPublishedDate(LocalDate.now());
        bookDetail.setCountry("국가");
        bookDetail.setNewBookStatus(false);
        bookDetailRepository.save(bookDetail);

        BookDto book = new BookDto();
        book.setPurchaseDate(LocalDate.now());

        //when
        bookService.registerBook(bookDetail.getId(), book);

        //then
        List<Book> books = bookRepository.findAll();
        assertThat(books.size()).isEqualTo(1);
    }

//    @DisplayName("도서 관리 등록 실패 - 예상 경우 없음")
//    @Test
//    void failedRegisterBook() {
//        //given
//        //when
//        //then
//    }

    @DisplayName("도서 관리 수정 성공")
    @Test
    void successModifyBook() {
        //given
        Book book = new Book();
        book.setPurchaseDate(LocalDate.now().minusDays(5));
        book.setRemarks("메모");
        bookRepository.save(book);

        BookDto modifiedBook = new BookDto();
        modifiedBook.setPurchaseDate(LocalDate.now());
        modifiedBook.setRemarks("수정된 메모");

        //when
        bookService.modifyBook(book.getId(), modifiedBook);

        //then
        assertThat(book.getPurchaseDate()).isEqualTo(LocalDate.now());
        assertThat(book.getRemarks()).isEqualTo("수정된 메모");
    }

//    @DisplayName("도서 관리 수정 실패 - 예상 경우 없음")
//    @Test
//    void failedModifyBook() {
//        //given
//        //when
//        //then
//    }

    @DisplayName("도서 관리 폐기 성공")
    @Test
    void successDiscardBook() {
        //given
        Book book1 = new Book();
        book1.setId(1L);
        book1.setPurchaseDate(LocalDate.now().minusDays(5));
        book1.setRemarks("메모");
        bookRepository.save(book1);

        Book book2 = new Book();
        book2.setId(2L);
        book2.setPurchaseDate(LocalDate.now().minusDays(5));
        book2.setRemarks("메모");
        bookRepository.save(book2);

        Book book3 = new Book();
        book3.setId(3L);
        book3.setPurchaseDate(LocalDate.now().minusDays(5));
        book3.setRemarks("메모");
        bookRepository.save(book3);

        //when
        bookService.discardBook(1L);

        //then
        List<Book> books = bookRepository.findAll();
        assertThat(books.size()).isEqualTo(2);
    }

//    @DisplayName("도서 관리 폐기 실패 - 예상 경우 없음")
//    @Test
//    void failedDiscardBook() {
//        //given
//        //when
//        //then
//    }
}
