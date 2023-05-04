package kr.mashup.branding.domain.notification;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNotification is a Querydsl query type for Notification
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNotification extends EntityPathBase<Notification> {

    private static final long serialVersionUID = 218366100L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNotification notification = new QNotification("notification");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath createdBy = createString("createdBy");

    public final kr.mashup.branding.domain.generation.QGeneration generation;

    public final StringPath messageId = createString("messageId");

    public final StringPath name = createString("name");

    public final NumberPath<Long> notificationId = createNumber("notificationId", Long.class);

    public final StringPath resultCode = createString("resultCode");

    public final StringPath resultId = createString("resultId");

    public final StringPath resultMessage = createString("resultMessage");

    public final kr.mashup.branding.domain.adminmember.entity.QAdminMember sender;

    public final StringPath senderValue = createString("senderValue");

    public final DateTimePath<java.time.LocalDateTime> sentAt = createDateTime("sentAt", java.time.LocalDateTime.class);

    public final ListPath<kr.mashup.branding.domain.notification.sms.SmsRequest, kr.mashup.branding.domain.notification.sms.QSmsRequest> smsRequests = this.<kr.mashup.branding.domain.notification.sms.SmsRequest, kr.mashup.branding.domain.notification.sms.QSmsRequest>createList("smsRequests", kr.mashup.branding.domain.notification.sms.SmsRequest.class, kr.mashup.branding.domain.notification.sms.QSmsRequest.class, PathInits.DIRECT2);

    public final EnumPath<NotificationStatus> status = createEnum("status", NotificationStatus.class);

    public final EnumPath<NotificationType> type = createEnum("type", NotificationType.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final StringPath updatedBy = createString("updatedBy");

    public QNotification(String variable) {
        this(Notification.class, forVariable(variable), INITS);
    }

    public QNotification(Path<? extends Notification> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNotification(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNotification(PathMetadata metadata, PathInits inits) {
        this(Notification.class, metadata, inits);
    }

    public QNotification(Class<? extends Notification> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.generation = inits.isInitialized("generation") ? new kr.mashup.branding.domain.generation.QGeneration(forProperty("generation")) : null;
        this.sender = inits.isInitialized("sender") ? new kr.mashup.branding.domain.adminmember.entity.QAdminMember(forProperty("sender")) : null;
    }

}

