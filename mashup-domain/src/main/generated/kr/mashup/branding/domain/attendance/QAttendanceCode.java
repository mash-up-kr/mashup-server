package kr.mashup.branding.domain.attendance;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAttendanceCode is a Querydsl query type for AttendanceCode
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAttendanceCode extends EntityPathBase<AttendanceCode> {

    private static final long serialVersionUID = -295598687L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAttendanceCode attendanceCode = new QAttendanceCode("attendanceCode");

    public final kr.mashup.branding.domain.QBaseEntity _super = new kr.mashup.branding.domain.QBaseEntity(this);

    public final DateTimePath<java.time.LocalDateTime> attendanceCheckEndedAt = createDateTime("attendanceCheckEndedAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> attendanceCheckStartedAt = createDateTime("attendanceCheckStartedAt", java.time.LocalDateTime.class);

    public final StringPath code = createString("code");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final kr.mashup.branding.domain.schedule.QEvent event;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final DateTimePath<java.time.LocalDateTime> latenessCheckEndedAt = createDateTime("latenessCheckEndedAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> latenessCheckStartedAt = createDateTime("latenessCheckStartedAt", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QAttendanceCode(String variable) {
        this(AttendanceCode.class, forVariable(variable), INITS);
    }

    public QAttendanceCode(Path<? extends AttendanceCode> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAttendanceCode(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAttendanceCode(PathMetadata metadata, PathInits inits) {
        this(AttendanceCode.class, metadata, inits);
    }

    public QAttendanceCode(Class<? extends AttendanceCode> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.event = inits.isInitialized("event") ? new kr.mashup.branding.domain.schedule.QEvent(forProperty("event"), inits.get("event")) : null;
    }

}

