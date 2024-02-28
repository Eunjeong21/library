package com.example.library.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.library.domain.BookDetail;
import com.example.library.dto.BookDetailDto;
import com.example.library.dto.SearchBookDto;
import com.example.library.repository.BookDetailRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class BookDetailServiceTest {

    @Autowired
    BookDetailRepository bookDetailRepository;
    @Autowired
    BookDetailService bookDetailService;

    @DisplayName("도서 상세 등록 성공")
    @Test
    void successRegisterBook() {
        //given
        BookDetailDto bookDetail = new BookDetailDto();
        bookDetail.setClassificationCode("분류기호");
        bookDetail.setTitle("제목");
        bookDetail.setAuthor("작가명");
        bookDetail.setPublisher("출판사");
        bookDetail.setPublishedDate(LocalDate.now());
        bookDetail.setCountry("국가");
        bookDetail.setNewBookStatus(false);

        //when
        bookDetailService.registerBook(bookDetail);

        //then
        BookDetail book = bookDetailRepository.findByTitle("제목");
        assertThat(book.getClassificationCode()).isEqualTo("분류기호");
        assertThat(book.getAuthor()).isEqualTo("작가명");
        assertThat(book.getPublisher()).isEqualTo("출판사");
        assertThat(book.getPublishedDate()).isEqualTo(LocalDate.now());
        assertThat(book.getCountry()).isEqualTo("국가");
        assertThat(book.isNewBookStatus()).isFalse();
    }

//    @DisplayName("도서 상세 등록 실패 - 예상 경우 없음")
//    @Test
//    void failedRegisterBook() {
//        //given
//        //when
//        //then
//    }

    @DisplayName("도서 상세 수정 성공")
    @Test
    void successModifyBook() {
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

        BookDetailDto modifiedBookDetail = new BookDetailDto();
        modifiedBookDetail.setClassificationCode("분류기호222");
        modifiedBookDetail.setTitle("제목222");
        modifiedBookDetail.setAuthor("작가명222");
        modifiedBookDetail.setPublisher("출판사222");
        modifiedBookDetail.setPublishedDate(LocalDate.now().plusDays(1));
        modifiedBookDetail.setCountry("국가222");
        modifiedBookDetail.setNewBookStatus(true);

        //when
        bookDetailService.modifyBook(1L, modifiedBookDetail);

        //then
        Optional<BookDetail> book = bookDetailRepository.findById(1L);
        assertThat(book.get().getClassificationCode()).isEqualTo("분류기호222");
        assertThat(book.get().getTitle()).isEqualTo("제목222");
        assertThat(book.get().getAuthor()).isEqualTo("작가명222");
        assertThat(book.get().getPublisher()).isEqualTo("출판사222");
        assertThat(book.get().getPublishedDate()).isEqualTo(LocalDate.now().plusDays(1));
        assertThat(book.get().getCountry()).isEqualTo("국가222");
        assertThat(book.get().isNewBookStatus()).isTrue();
    }

//    @DisplayName("도서 상세 수정 실패 - 예상 경우 없음")
//    @Test
//    void failedModifyBook() {
//        //given
//        //when
//        //then
//    }

    @DisplayName("도서 상세 폐기 성공")
    @Test
    void successDiscardBook() {
        //given
        BookDetail bookDetail1 = new BookDetail();
        bookDetail1.setId(1L);
        bookDetail1.setClassificationCode("분류기호");
        bookDetail1.setTitle("제목");
        bookDetail1.setAuthor("작가명");
        bookDetail1.setPublisher("출판사");
        bookDetail1.setPublishedDate(LocalDate.now());
        bookDetail1.setCountry("국가");
        bookDetail1.setNewBookStatus(false);
        bookDetailRepository.save(bookDetail1);

        BookDetail bookDetail2 = new BookDetail();
        bookDetail2.setId(2L);
        bookDetail2.setClassificationCode("분류기호222");
        bookDetail2.setTitle("제목222");
        bookDetail2.setAuthor("작가명222");
        bookDetail2.setPublisher("출판사222");
        bookDetail2.setPublishedDate(LocalDate.now().plusDays(1));
        bookDetail2.setCountry("국가222");
        bookDetail2.setNewBookStatus(true);
        bookDetailRepository.save(bookDetail2);

        //when
        bookDetailService.discardBook(1L);

        //then
        List<BookDetail> details = bookDetailRepository.findAll();
        assertThat(details.size()).isEqualTo(1);
    }

//    @DisplayName("도서 상세 폐기 실패 - 예상 경우 없음")
//    @Test
//    void failedDiscardBook() {
//        //given
//        //when
//        //then
//    }

    @DisplayName("도서 검색 성공 - 제목 포함")
    @Test
    void successSearchLikeTitle() {
        //given
        BookDetail bookDetail1 = new BookDetail();
        bookDetail1.setId(1L);
        bookDetail1.setClassificationCode("분류기호");
        bookDetail1.setTitle("제목");
        bookDetail1.setAuthor("작가명");
        bookDetail1.setPublisher("출판사");
        bookDetail1.setPublishedDate(LocalDate.now());
        bookDetail1.setCountry("국가");
        bookDetail1.setNewBookStatus(false);
        bookDetailRepository.save(bookDetail1);

        BookDetail bookDetail2 = new BookDetail();
        bookDetail2.setId(2L);
        bookDetail2.setClassificationCode("분류기호222");
        bookDetail2.setTitle("제목222");
        bookDetail2.setAuthor("작가명222");
        bookDetail2.setPublisher("출판사222");
        bookDetail2.setPublishedDate(LocalDate.now().plusDays(1));
        bookDetail2.setCountry("국가222");
        bookDetail2.setNewBookStatus(true);
        bookDetailRepository.save(bookDetail2);

        BookDetail bookDetail3 = new BookDetail();
        bookDetail3.setId(3L);
        bookDetail3.setClassificationCode("분류기호333");
        bookDetail3.setTitle("테스트 데이터");
        bookDetail3.setAuthor("작가명333");
        bookDetail3.setPublisher("출판사333");
        bookDetail3.setPublishedDate(LocalDate.now().plusDays(1));
        bookDetail3.setCountry("국가333");
        bookDetail3.setNewBookStatus(true);
        bookDetailRepository.save(bookDetail3);

        //when
        SearchBookDto keyword = new SearchBookDto();
        keyword.setTitle("제목");
        List<BookDetail> bookDetails = bookDetailService.searchKeywordBook(keyword);

        //then
        assertThat(bookDetails.size()).isEqualTo(2);
    }

    @DisplayName("도서 검색 성공 - 작가명 포함")
    @Test
    void successSearchLikeAuthor() {
        //given
        BookDetail bookDetail1 = new BookDetail();
        bookDetail1.setId(1L);
        bookDetail1.setClassificationCode("분류기호");
        bookDetail1.setTitle("제목");
        bookDetail1.setAuthor("작가명");
        bookDetail1.setPublisher("출판사");
        bookDetail1.setPublishedDate(LocalDate.now());
        bookDetail1.setCountry("국가");
        bookDetail1.setNewBookStatus(false);
        bookDetailRepository.save(bookDetail1);

        BookDetail bookDetail2 = new BookDetail();
        bookDetail2.setId(2L);
        bookDetail2.setClassificationCode("분류기호222");
        bookDetail2.setTitle("제목222");
        bookDetail2.setAuthor("작가명222");
        bookDetail2.setPublisher("출판사222");
        bookDetail2.setPublishedDate(LocalDate.now().plusDays(1));
        bookDetail2.setCountry("국가222");
        bookDetail2.setNewBookStatus(true);
        bookDetailRepository.save(bookDetail2);

        BookDetail bookDetail3 = new BookDetail();
        bookDetail3.setId(3L);
        bookDetail3.setClassificationCode("분류기호333");
        bookDetail3.setTitle("제목333");
        bookDetail3.setAuthor("테스트 데이터");
        bookDetail3.setPublisher("출판사333");
        bookDetail3.setPublishedDate(LocalDate.now().plusDays(1));
        bookDetail3.setCountry("국가333");
        bookDetail3.setNewBookStatus(true);
        bookDetailRepository.save(bookDetail3);

        //when
        SearchBookDto keyword = new SearchBookDto();
        keyword.setAuthor("작가");
        List<BookDetail> bookDetails = bookDetailService.searchKeywordBook(keyword);

        //then
        assertThat(bookDetails.size()).isEqualTo(2);
    }

//    @DisplayName("도서 검색 실패 - 존재하지 않는 검색어 -> 예외처리가 필수인가?")
//    @Test
//    void failedSearchKeywordBook() {
//        //given
//        //when
//        //then
//    }
}
