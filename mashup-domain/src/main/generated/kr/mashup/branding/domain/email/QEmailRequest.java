package kr.mashup.branding.domain.email;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEmailRequest is a Querydsl query type for EmailRequest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmailRequest extends EntityPathBase<EmailRequest> {

    private static final long serialVersionUID = -731896865L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEmailRequest emailRequest = new QEmailRequest("emailRequest");

    public final kr.mashup.branding.domain.application.QApplication application;

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final QEmailNotification emailNotification;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath messageId = createString("messageId");

    public final EnumPath<EmailRequestStatus> status = createEnum("status", EmailRequestStatus.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QEmailRequest(String variable) {
        this(EmailRequest.class, forVariable(variable), INITS);
    }

    public QEmailRequest(Path<? extends EmailRequest> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEmailRequest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEmailRequest(PathMetadata metadata, PathInits inits) {
        this(EmailRequest.class, metadata, inits);
    }

    public QEmailRequest(Class<? extends EmailRequest> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.application = inits.isInitialized("application") ? new kr.mashup.branding.domain.application.QApplication(forProperty("application"), inits.get("application")) : null;
        this.emailNotification = inits.isInitialized("emailNotification") ? new QEmailNotification(forProperty("emailNotification"), inits.get("emailNotification")) : null;
    }

}

