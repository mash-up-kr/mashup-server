package kr.mashup.branding.domain.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberGeneration is a Querydsl query type for MemberGeneration
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberGeneration extends EntityPathBase<MemberGeneration> {

    private static final long serialVersionUID = -835516116L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberGeneration memberGeneration = new QMemberGeneration("memberGeneration");

    public final kr.mashup.branding.domain.QBaseEntity _super = new kr.mashup.branding.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final kr.mashup.branding.domain.generation.QGeneration generation;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final QMember member;

    public final EnumPath<Platform> platform = createEnum("platform", Platform.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QMemberGeneration(String variable) {
        this(MemberGeneration.class, forVariable(variable), INITS);
    }

    public QMemberGeneration(Path<? extends MemberGeneration> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberGeneration(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberGeneration(PathMetadata metadata, PathInits inits) {
        this(MemberGeneration.class, metadata, inits);
    }

    public QMemberGeneration(Class<? extends MemberGeneration> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.generation = inits.isInitialized("generation") ? new kr.mashup.branding.domain.generation.QGeneration(forProperty("generation")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

