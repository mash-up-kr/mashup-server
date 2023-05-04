package kr.mashup.branding.domain.email;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEmailNotification is a Querydsl query type for EmailNotification
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmailNotification extends EntityPathBase<EmailNotification> {

    private static final long serialVersionUID = -117200485L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEmailNotification emailNotification = new QEmailNotification("emailNotification");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final ListPath<EmailRequest, QEmailRequest> emailRequests = this.<EmailRequest, QEmailRequest>createList("emailRequests", EmailRequest.class, QEmailRequest.class, PathInits.DIRECT2);

    public final EnumPath<EmailTemplateName> emailTemplateName = createEnum("emailTemplateName", EmailTemplateName.class);

    public final kr.mashup.branding.domain.generation.QGeneration generation;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath memo = createString("memo");

    public final kr.mashup.branding.domain.adminmember.entity.QAdminMember sender;

    public final StringPath senderEmail = createString("senderEmail");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QEmailNotification(String variable) {
        this(EmailNotification.class, forVariable(variable), INITS);
    }

    public QEmailNotification(Path<? extends EmailNotification> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEmailNotification(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEmailNotification(PathMetadata metadata, PathInits inits) {
        this(EmailNotification.class, metadata, inits);
    }

    public QEmailNotification(Class<? extends EmailNotification> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.generation = inits.isInitialized("generation") ? new kr.mashup.branding.domain.generation.QGeneration(forProperty("generation")) : null;
        this.sender = inits.isInitialized("sender") ? new kr.mashup.branding.domain.adminmember.entity.QAdminMember(forProperty("sender")) : null;
    }

}

