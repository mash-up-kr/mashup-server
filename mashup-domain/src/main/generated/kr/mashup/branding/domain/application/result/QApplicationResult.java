package kr.mashup.branding.domain.application.result;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QApplicationResult is a Querydsl query type for ApplicationResult
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QApplicationResult extends EntityPathBase<ApplicationResult> {

    private static final long serialVersionUID = 2087140756L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QApplicationResult applicationResult = new QApplicationResult("applicationResult");

    public final kr.mashup.branding.domain.application.QApplication application;

    public final NumberPath<Long> applicationResultId = createNumber("applicationResultId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath createdBy = createString("createdBy");

    public final DateTimePath<java.time.LocalDateTime> interviewEndedAt = createDateTime("interviewEndedAt", java.time.LocalDateTime.class);

    public final StringPath interviewGuideLink = createString("interviewGuideLink");

    public final DateTimePath<java.time.LocalDateTime> interviewStartedAt = createDateTime("interviewStartedAt", java.time.LocalDateTime.class);

    public final EnumPath<ApplicationInterviewStatus> interviewStatus = createEnum("interviewStatus", ApplicationInterviewStatus.class);

    public final EnumPath<ApplicationScreeningStatus> screeningStatus = createEnum("screeningStatus", ApplicationScreeningStatus.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final StringPath updatedBy = createString("updatedBy");

    public QApplicationResult(String variable) {
        this(ApplicationResult.class, forVariable(variable), INITS);
    }

    public QApplicationResult(Path<? extends ApplicationResult> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QApplicationResult(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QApplicationResult(PathMetadata metadata, PathInits inits) {
        this(ApplicationResult.class, metadata, inits);
    }

    public QApplicationResult(Class<? extends ApplicationResult> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.application = inits.isInitialized("application") ? new kr.mashup.branding.domain.application.QApplication(forProperty("application"), inits.get("application")) : null;
    }

}

