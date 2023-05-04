package kr.mashup.branding.domain.notification.sms;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSmsRequest is a Querydsl query type for SmsRequest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSmsRequest extends EntityPathBase<SmsRequest> {

    private static final long serialVersionUID = -961165292L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSmsRequest smsRequest = new QSmsRequest("smsRequest");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath createdBy = createString("createdBy");

    public final StringPath messageId = createString("messageId");

    public final kr.mashup.branding.domain.notification.QNotification notification;

    public final kr.mashup.branding.domain.applicant.QApplicant recipientApplicant;

    public final StringPath recipientPhoneNumber = createString("recipientPhoneNumber");

    public final StringPath resultCode = createString("resultCode");

    public final StringPath resultId = createString("resultId");

    public final StringPath resultMessage = createString("resultMessage");

    public final NumberPath<Long> smsRequestId = createNumber("smsRequestId", Long.class);

    public final EnumPath<SmsNotificationStatus> status = createEnum("status", SmsNotificationStatus.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final StringPath updatedBy = createString("updatedBy");

    public QSmsRequest(String variable) {
        this(SmsRequest.class, forVariable(variable), INITS);
    }

    public QSmsRequest(Path<? extends SmsRequest> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSmsRequest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSmsRequest(PathMetadata metadata, PathInits inits) {
        this(SmsRequest.class, metadata, inits);
    }

    public QSmsRequest(Class<? extends SmsRequest> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.notification = inits.isInitialized("notification") ? new kr.mashup.branding.domain.notification.QNotification(forProperty("notification"), inits.get("notification")) : null;
        this.recipientApplicant = inits.isInitialized("recipientApplicant") ? new kr.mashup.branding.domain.applicant.QApplicant(forProperty("recipientApplicant")) : null;
    }

}

