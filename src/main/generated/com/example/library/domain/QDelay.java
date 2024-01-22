package com.example.library.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDelay is a Querydsl query type for Delay
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDelay extends EntityPathBase<Delay> {

    private static final long serialVersionUID = -198618787L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDelay delay = new QDelay("delay");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLending lending;

    public final DatePath<java.time.LocalDate> loanableTransitionDate = createDate("loanableTransitionDate", java.time.LocalDate.class);

    public QDelay(String variable) {
        this(Delay.class, forVariable(variable), INITS);
    }

    public QDelay(Path<? extends Delay> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDelay(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDelay(PathMetadata metadata, PathInits inits) {
        this(Delay.class, metadata, inits);
    }

    public QDelay(Class<? extends Delay> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.lending = inits.isInitialized("lending") ? new QLending(forProperty("lending"), inits.get("lending")) : null;
    }

}

