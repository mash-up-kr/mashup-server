package kr.mashup.branding.domain.danggn;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDanggnScore is a Querydsl query type for DanggnScore
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDanggnScore extends EntityPathBase<DanggnScore> {

    private static final long serialVersionUID = -119572642L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDanggnScore danggnScore = new QDanggnScore("danggnScore");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> lastShakedAt = createDateTime("lastShakedAt", java.time.LocalDateTime.class);

    public final kr.mashup.branding.domain.member.QMemberGeneration memberGeneration;

    public final NumberPath<Long> totalShakeScore = createNumber("totalShakeScore", Long.class);

    public QDanggnScore(String variable) {
        this(DanggnScore.class, forVariable(variable), INITS);
    }

    public QDanggnScore(Path<? extends DanggnScore> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDanggnScore(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDanggnScore(PathMetadata metadata, PathInits inits) {
        this(DanggnScore.class, metadata, inits);
    }

    public QDanggnScore(Class<? extends DanggnScore> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.memberGeneration = inits.isInitialized("memberGeneration") ? new kr.mashup.branding.domain.member.QMemberGeneration(forProperty("memberGeneration"), inits.get("memberGeneration")) : null;
    }

}

