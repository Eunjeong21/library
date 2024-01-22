package com.example.library.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBookDetail is a Querydsl query type for BookDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBookDetail extends EntityPathBase<BookDetail> {

    private static final long serialVersionUID = -356258560L;

    public static final QBookDetail bookDetail = new QBookDetail("bookDetail");

    public final StringPath author = createString("author");

    public final StringPath classificationCode = createString("classificationCode");

    public final StringPath country = createString("country");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath newBookStatus = createBoolean("newBookStatus");

    public final DatePath<java.time.LocalDate> publishedDate = createDate("publishedDate", java.time.LocalDate.class);

    public final StringPath publisher = createString("publisher");

    public final StringPath title = createString("title");

    public QBookDetail(String variable) {
        super(BookDetail.class, forVariable(variable));
    }

    public QBookDetail(Path<? extends BookDetail> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBookDetail(PathMetadata metadata) {
        super(BookDetail.class, metadata);
    }

}

