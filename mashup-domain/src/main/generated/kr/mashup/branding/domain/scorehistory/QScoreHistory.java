package kr.mashup.branding.domain.scorehistory;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QScoreHistory is a Querydsl query type for ScoreHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QScoreHistory extends EntityPathBase<ScoreHistory> {

    private static final long serialVersionUID = 1133342164L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QScoreHistory scoreHistory = new QScoreHistory("scoreHistory");

    public final kr.mashup.branding.domain.QBaseEntity _super = new kr.mashup.branding.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DateTimePath<java.time.LocalDateTime> date = createDateTime("date", java.time.LocalDateTime.class);

    public final kr.mashup.branding.domain.generation.QGeneration generation;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final BooleanPath isCanceled = createBoolean("isCanceled");

    public final kr.mashup.branding.domain.member.QMember member;

    public final StringPath memo = createString("memo");

    public final StringPath name = createString("name");

    public final StringPath scheduleName = createString("scheduleName");

    public final NumberPath<Double> score = createNumber("score", Double.class);

    public final StringPath type = createString("type");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QScoreHistory(String variable) {
        this(ScoreHistory.class, forVariable(variable), INITS);
    }

    public QScoreHistory(Path<? extends ScoreHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QScoreHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QScoreHistory(PathMetadata metadata, PathInits inits) {
        this(ScoreHistory.class, metadata, inits);
    }

    public QScoreHistory(Class<? extends ScoreHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.generation = inits.isInitialized("generation") ? new kr.mashup.branding.domain.generation.QGeneration(forProperty("generation")) : null;
        this.member = inits.isInitialized("member") ? new kr.mashup.branding.domain.member.QMember(forProperty("member")) : null;
    }

}

