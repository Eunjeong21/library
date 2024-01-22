package com.example.library.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLending is a Querydsl query type for Lending
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLending extends EntityPathBase<Lending> {

    private static final long serialVersionUID = 912893421L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLending lending = new QLending("lending");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<LendingDetail, QLendingDetail> lendingDetails = this.<LendingDetail, QLendingDetail>createList("lendingDetails", LendingDetail.class, QLendingDetail.class, PathInits.DIRECT2);

    public final DatePath<java.time.LocalDate> loanDate = createDate("loanDate", java.time.LocalDate.class);

    public final QMember member;

    public QLending(String variable) {
        this(Lending.class, forVariable(variable), INITS);
    }

    public QLending(Path<? extends Lending> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLending(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLending(PathMetadata metadata, PathInits inits) {
        this(Lending.class, metadata, inits);
    }

    public QLending(Class<? extends Lending> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

