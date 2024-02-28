package com.example.library.service.impl;

import static com.example.library.domain.QBookDetail.bookDetail;

import com.example.library.domain.BookDetail;
import com.example.library.dto.BookDetailDto;
import com.example.library.dto.SearchBookDto;
import com.example.library.repository.BookDetailRepository;
import com.example.library.service.BookDetailService;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class BookDetailServiceImpl implements BookDetailService {

    private final BookDetailRepository bookDetailRepository;
    private final JPAQueryFactory query;

    @Override
    public void registerBook(BookDetailDto request) {
        BookDetail book = new BookDetail();
        book.setClassificationCode(request.getClassificationCode());
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPublisher(request.getPublisher());
        book.setPublishedDate(request.getPublishedDate());
        book.setCountry(request.getCountry());
        book.setNewBookStatus(request.isNewBookStatus());

        bookDetailRepository.save(book);
    }

    @Override
    public void modifyBook(Long bookId, BookDetailDto request) {
        BookDetail book = bookDetailRepository.findById(bookId)
            .orElseThrow(IllegalArgumentException::new);
        book.setClassificationCode(request.getClassificationCode());
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPublisher(request.getPublisher());
        book.setPublishedDate(request.getPublishedDate());
        book.setCountry(request.getCountry());
        book.setNewBookStatus(request.isNewBookStatus());
    }

    @Override
    public void discardBook(Long bookId) {
        bookDetailRepository.findById(bookId)
            .orElseThrow(IllegalArgumentException::new);

        bookDetailRepository.deleteById(bookId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDetail> searchKeywordBook(SearchBookDto keyword) {

        String title = keyword.getTitle();
        String author = keyword.getAuthor();

        return query
            .select(bookDetail)
            .from(bookDetail)
            .where(likeTitle(title), likeAuthor(author))
            .fetch();
    }

    private BooleanExpression likeTitle(String title) {
        if (StringUtils.hasText(title)) {
            return bookDetail.title.like("%" + title + "%");
        }
        return null;
    }

    private BooleanExpression likeAuthor(String author) {
        if (StringUtils.hasText(author)) {
            return bookDetail.author.like("%" + author + "%");
        }
        return null;
    }
}
