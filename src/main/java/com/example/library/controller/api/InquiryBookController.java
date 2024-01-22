package com.example.library.controller.api;

import com.example.library.domain.BookDetail;
import com.example.library.dto.ResponseBookDetailDto;
import com.example.library.dto.ResponseDto;
import com.example.library.dto.SearchBookDto;
import com.example.library.service.BookDetailService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class InquiryBookController {

    private final BookDetailService bookDetailService;

    @GetMapping("/{keyword}")
    public ResponseEntity<ResponseDto> search(@PathVariable @Valid SearchBookDto keyword) {
        List<BookDetail> bookDetails = bookDetailService.searchKeywordBook(keyword);
        List<ResponseBookDetailDto> responseBookDetails = bookDetails.stream()
            .map(b -> new ResponseBookDetailDto(
                b.getClassificationCode(),
                b.getTitle(),
                b.getAuthor(),
                b.getPublisher(),
                b.getPublishedDate(),
                b.getCountry(),
                b.isNewBookStatus()
            )).toList();
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ResponseDto.builder()
                .status(HttpStatus.OK)
                .data(responseBookDetails)
                .build());
    }
}
