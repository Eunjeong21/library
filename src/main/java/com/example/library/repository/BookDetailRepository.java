package com.example.library.repository;

import com.example.library.domain.BookDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookDetailRepository extends JpaRepository<BookDetail, Long> {
    BookDetail findByTitle(String title);
}
