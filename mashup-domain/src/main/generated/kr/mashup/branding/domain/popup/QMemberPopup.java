package kr.mashup.branding.domain.popup;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberPopup is a Querydsl query type for MemberPopup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberPopup extends EntityPathBase<MemberPopup> {

    private static final long serialVersionUID = -210574026L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberPopup memberPopup = new QMemberPopup("memberPopup");

    public final kr.mashup.branding.domain.QBaseEntity _super = new kr.mashup.branding.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final BooleanPath isEnabled = createBoolean("isEnabled");

    public final DateTimePath<java.time.LocalDateTime> lastViewedAt = createDateTime("lastViewedAt", java.time.LocalDateTime.class);

    public final kr.mashup.branding.domain.member.QMember member;

    public final EnumPath<PopupType> popupType = createEnum("popupType", PopupType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QMemberPopup(String variable) {
        this(MemberPopup.class, forVariable(variable), INITS);
    }

    public QMemberPopup(Path<? extends MemberPopup> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberPopup(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberPopup(PathMetadata metadata, PathInits inits) {
        this(MemberPopup.class, metadata, inits);
    }

    public QMemberPopup(Class<? extends MemberPopup> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new kr.mashup.branding.domain.member.QMember(forProperty("member")) : null;
    }

}

