package kr.mashup.branding.domain.danggn;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDanggnShakeLog is a Querydsl query type for DanggnShakeLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDanggnShakeLog extends EntityPathBase<DanggnShakeLog> {

    private static final long serialVersionUID = -1925334510L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDanggnShakeLog danggnShakeLog = new QDanggnShakeLog("danggnShakeLog");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final kr.mashup.branding.domain.member.QMemberGeneration memberGeneration;

    public final NumberPath<Long> shakeScore = createNumber("shakeScore", Long.class);

    public QDanggnShakeLog(String variable) {
        this(DanggnShakeLog.class, forVariable(variable), INITS);
    }

    public QDanggnShakeLog(Path<? extends DanggnShakeLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDanggnShakeLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDanggnShakeLog(PathMetadata metadata, PathInits inits) {
        this(DanggnShakeLog.class, metadata, inits);
    }

    public QDanggnShakeLog(Class<? extends DanggnShakeLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.memberGeneration = inits.isInitialized("memberGeneration") ? new kr.mashup.branding.domain.member.QMemberGeneration(forProperty("memberGeneration"), inits.get("memberGeneration")) : null;
    }

}

