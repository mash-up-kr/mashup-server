package kr.mashup.branding.domain.application.confirmation;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QConfirmation is a Querydsl query type for Confirmation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QConfirmation extends EntityPathBase<Confirmation> {

    private static final long serialVersionUID = -1639763786L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QConfirmation confirmation = new QConfirmation("confirmation");

    public final kr.mashup.branding.domain.application.QApplication application;

    public final NumberPath<Long> confirmationId = createNumber("confirmationId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath rejectionReason = createString("rejectionReason");

    public final EnumPath<ApplicantConfirmationStatus> status = createEnum("status", ApplicantConfirmationStatus.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QConfirmation(String variable) {
        this(Confirmation.class, forVariable(variable), INITS);
    }

    public QConfirmation(Path<? extends Confirmation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QConfirmation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QConfirmation(PathMetadata metadata, PathInits inits) {
        this(Confirmation.class, metadata, inits);
    }

    public QConfirmation(Class<? extends Confirmation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.application = inits.isInitialized("application") ? new kr.mashup.branding.domain.application.QApplication(forProperty("application"), inits.get("application")) : null;
    }

}

