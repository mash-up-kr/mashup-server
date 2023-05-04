package kr.mashup.branding.domain.application.form;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QApplicationForm is a Querydsl query type for ApplicationForm
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QApplicationForm extends EntityPathBase<ApplicationForm> {

    private static final long serialVersionUID = -778463070L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QApplicationForm applicationForm = new QApplicationForm("applicationForm");

    public final NumberPath<Long> applicationFormId = createNumber("applicationFormId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath createdBy = createString("createdBy");

    public final StringPath name = createString("name");

    public final ListPath<Question, QQuestion> questions = this.<Question, QQuestion>createList("questions", Question.class, QQuestion.class, PathInits.DIRECT2);

    public final kr.mashup.branding.domain.team.QTeam team;

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final StringPath updatedBy = createString("updatedBy");

    public QApplicationForm(String variable) {
        this(ApplicationForm.class, forVariable(variable), INITS);
    }

    public QApplicationForm(Path<? extends ApplicationForm> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QApplicationForm(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QApplicationForm(PathMetadata metadata, PathInits inits) {
        this(ApplicationForm.class, metadata, inits);
    }

    public QApplicationForm(Class<? extends ApplicationForm> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.team = inits.isInitialized("team") ? new kr.mashup.branding.domain.team.QTeam(forProperty("team"), inits.get("team")) : null;
    }

}

