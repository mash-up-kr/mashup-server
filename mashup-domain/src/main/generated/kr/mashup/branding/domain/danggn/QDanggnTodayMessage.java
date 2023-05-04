package kr.mashup.branding.domain.danggn;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDanggnTodayMessage is a Querydsl query type for DanggnTodayMessage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDanggnTodayMessage extends EntityPathBase<DanggnTodayMessage> {

    private static final long serialVersionUID = 1396508474L;

    public static final QDanggnTodayMessage danggnTodayMessage = new QDanggnTodayMessage("danggnTodayMessage");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath message = createString("message");

    public QDanggnTodayMessage(String variable) {
        super(DanggnTodayMessage.class, forVariable(variable));
    }

    public QDanggnTodayMessage(Path<? extends DanggnTodayMessage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDanggnTodayMessage(PathMetadata metadata) {
        super(DanggnTodayMessage.class, metadata);
    }

}

