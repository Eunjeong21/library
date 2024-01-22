package com.example.library.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLendingDetail is a Querydsl query type for LendingDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLendingDetail extends EntityPathBase<LendingDetail> {

    private static final long serialVersionUID = -624048994L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLendingDetail lendingDetail = new QLendingDetail("lendingDetail");

    public final QBook book;

    public final DatePath<java.time.LocalDate> expectedReturnDate = createDate("expectedReturnDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLending lending;

    public final QMember member;

    public final BooleanPath returnStatus = createBoolean("returnStatus");

    public QLendingDetail(String variable) {
        this(LendingDetail.class, forVariable(variable), INITS);
    }

    public QLendingDetail(Path<? extends LendingDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLendingDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLendingDetail(PathMetadata metadata, PathInits inits) {
        this(LendingDetail.class, metadata, inits);
    }

    public QLendingDetail(Class<? extends LendingDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.book = inits.isInitialized("book") ? new QBook(forProperty("book"), inits.get("book")) : null;
        this.lending = inits.isInitialized("lending") ? new QLending(forProperty("lending"), inits.get("lending")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

