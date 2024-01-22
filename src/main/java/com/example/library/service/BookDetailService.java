package com.example.library.service;

import com.example.library.domain.BookDetail;
import com.example.library.dto.BookDetailDto;
import com.example.library.dto.SearchBookDto;
import java.util.List;

public interface BookDetailService {

    // 책 등록, 수정, 삭제, 조회
    // 현재는 soft delete 회원과 달리 책 삭제는 hard delete
    // 폐기한 책에 대한 별도의 저장(soft delete 또는 새로운 테이블)과 컬럼이 필요한가? -> 고려해봐야 할듯

    void registerBook(BookDetailDto request);

    void modifyBook(Long bookId, BookDetailDto request);

    void discardBook(Long bookId);

    List<BookDetail> searchKeywordBook(SearchBookDto request);
}
