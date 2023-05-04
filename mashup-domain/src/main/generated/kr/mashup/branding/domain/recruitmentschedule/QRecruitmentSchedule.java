package kr.mashup.branding.domain.recruitmentschedule;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecruitmentSchedule is a Querydsl query type for RecruitmentSchedule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecruitmentSchedule extends EntityPathBase<RecruitmentSchedule> {

    private static final long serialVersionUID = 403514078L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecruitmentSchedule recruitmentSchedule = new QRecruitmentSchedule("recruitmentSchedule");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath createdBy = createString("createdBy");

    public final EnumPath<RecruitmentScheduleEventName> eventName = createEnum("eventName", RecruitmentScheduleEventName.class);

    public final DateTimePath<java.time.LocalDateTime> eventOccurredAt = createDateTime("eventOccurredAt", java.time.LocalDateTime.class);

    public final kr.mashup.branding.domain.generation.QGeneration generation;

    public final NumberPath<Long> recruitmentScheduleId = createNumber("recruitmentScheduleId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final StringPath updatedBy = createString("updatedBy");

    public QRecruitmentSchedule(String variable) {
        this(RecruitmentSchedule.class, forVariable(variable), INITS);
    }

    public QRecruitmentSchedule(Path<? extends RecruitmentSchedule> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecruitmentSchedule(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecruitmentSchedule(PathMetadata metadata, PathInits inits) {
        this(RecruitmentSchedule.class, metadata, inits);
    }

    public QRecruitmentSchedule(Class<? extends RecruitmentSchedule> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.generation = inits.isInitialized("generation") ? new kr.mashup.branding.domain.generation.QGeneration(forProperty("generation")) : null;
    }

}

