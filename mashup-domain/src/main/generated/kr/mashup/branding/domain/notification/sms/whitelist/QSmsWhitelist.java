package kr.mashup.branding.domain.notification.sms.whitelist;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSmsWhitelist is a Querydsl query type for SmsWhitelist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSmsWhitelist extends EntityPathBase<SmsWhitelist> {

    private static final long serialVersionUID = 1918720339L;

    public static final QSmsWhitelist smsWhitelist = new QSmsWhitelist("smsWhitelist");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath createdBy = createString("createdBy");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final NumberPath<Long> smsWhiteListId = createNumber("smsWhiteListId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final StringPath updatedBy = createString("updatedBy");

    public QSmsWhitelist(String variable) {
        super(SmsWhitelist.class, forVariable(variable));
    }

    public QSmsWhitelist(Path<? extends SmsWhitelist> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSmsWhitelist(PathMetadata metadata) {
        super(SmsWhitelist.class, metadata);
    }

}

