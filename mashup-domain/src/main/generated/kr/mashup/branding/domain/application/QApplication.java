package kr.mashup.branding.domain.application;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QApplication is a Querydsl query type for Application
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QApplication extends EntityPathBase<Application> {

    private static final long serialVersionUID = 171441784L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QApplication application = new QApplication("application");

    public final ListPath<Answer, QAnswer> answers = this.<Answer, QAnswer>createList("answers", Answer.class, QAnswer.class, PathInits.DIRECT2);

    public final kr.mashup.branding.domain.applicant.QApplicant applicant;

    public final kr.mashup.branding.domain.application.form.QApplicationForm applicationForm;

    public final NumberPath<Long> applicationId = createNumber("applicationId", Long.class);

    public final kr.mashup.branding.domain.application.result.QApplicationResult applicationResult;

    public final kr.mashup.branding.domain.application.confirmation.QConfirmation confirmation;

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final BooleanPath privacyPolicyAgreed = createBoolean("privacyPolicyAgreed");

    public final EnumPath<ApplicationStatus> status = createEnum("status", ApplicationStatus.class);

    public final DateTimePath<java.time.LocalDateTime> submittedAt = createDateTime("submittedAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QApplication(String variable) {
        this(Application.class, forVariable(variable), INITS);
    }

    public QApplication(Path<? extends Application> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QApplication(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QApplication(PathMetadata metadata, PathInits inits) {
        this(Application.class, metadata, inits);
    }

    public QApplication(Class<? extends Application> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.applicant = inits.isInitialized("applicant") ? new kr.mashup.branding.domain.applicant.QApplicant(forProperty("applicant")) : null;
        this.applicationForm = inits.isInitialized("applicationForm") ? new kr.mashup.branding.domain.application.form.QApplicationForm(forProperty("applicationForm"), inits.get("applicationForm")) : null;
        this.applicationResult = inits.isInitialized("applicationResult") ? new kr.mashup.branding.domain.application.result.QApplicationResult(forProperty("applicationResult"), inits.get("applicationResult")) : null;
        this.confirmation = inits.isInitialized("confirmation") ? new kr.mashup.branding.domain.application.confirmation.QConfirmation(forProperty("confirmation"), inits.get("confirmation")) : null;
    }

}

