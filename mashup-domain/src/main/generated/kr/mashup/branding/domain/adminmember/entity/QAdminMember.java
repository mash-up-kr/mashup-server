package kr.mashup.branding.domain.adminmember.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAdminMember is a Querydsl query type for AdminMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdminMember extends EntityPathBase<AdminMember> {

    private static final long serialVersionUID = 1660652765L;

    public static final QAdminMember adminMember = new QAdminMember("adminMember");

    public final NumberPath<Long> adminMemberId = createNumber("adminMemberId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath createdBy = createString("createdBy");

    public final StringPath password = createString("password");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final BooleanPath phoneNumberRegistered = createBoolean("phoneNumberRegistered");

    public final EnumPath<Position> position = createEnum("position", Position.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final StringPath updatedBy = createString("updatedBy");

    public final StringPath username = createString("username");

    public QAdminMember(String variable) {
        super(AdminMember.class, forVariable(variable));
    }

    public QAdminMember(Path<? extends AdminMember> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAdminMember(PathMetadata metadata) {
        super(AdminMember.class, metadata);
    }

}

