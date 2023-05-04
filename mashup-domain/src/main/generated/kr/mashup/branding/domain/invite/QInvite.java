package kr.mashup.branding.domain.invite;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInvite is a Querydsl query type for Invite
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInvite extends EntityPathBase<Invite> {

    private static final long serialVersionUID = 206379540L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInvite invite = new QInvite("invite");

    public final kr.mashup.branding.domain.QBaseEntity _super = new kr.mashup.branding.domain.QBaseEntity(this);

    public final StringPath code = createString("code");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DateTimePath<java.time.LocalDateTime> endedAt = createDateTime("endedAt", java.time.LocalDateTime.class);

    public final kr.mashup.branding.domain.generation.QGeneration generation;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Integer> limitCount = createNumber("limitCount", Integer.class);

    public final EnumPath<kr.mashup.branding.domain.member.Platform> platform = createEnum("platform", kr.mashup.branding.domain.member.Platform.class);

    public final DateTimePath<java.time.LocalDateTime> startedAt = createDateTime("startedAt", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QInvite(String variable) {
        this(Invite.class, forVariable(variable), INITS);
    }

    public QInvite(Path<? extends Invite> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInvite(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInvite(PathMetadata metadata, PathInits inits) {
        this(Invite.class, metadata, inits);
    }

    public QInvite(Class<? extends Invite> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.generation = inits.isInitialized("generation") ? new kr.mashup.branding.domain.generation.QGeneration(forProperty("generation")) : null;
    }

}

