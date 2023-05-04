package kr.mashup.branding.domain.generation;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGeneration is a Querydsl query type for Generation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGeneration extends EntityPathBase<Generation> {

    private static final long serialVersionUID = -1718621644L;

    public static final QGeneration generation = new QGeneration("generation");

    public final kr.mashup.branding.domain.QBaseEntity _super = new kr.mashup.branding.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DatePath<java.time.LocalDate> endedAt = createDate("endedAt", java.time.LocalDate.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Integer> number = createNumber("number", Integer.class);

    public final DatePath<java.time.LocalDate> startedAt = createDate("startedAt", java.time.LocalDate.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QGeneration(String variable) {
        super(Generation.class, forVariable(variable));
    }

    public QGeneration(Path<? extends Generation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGeneration(PathMetadata metadata) {
        super(Generation.class, metadata);
    }

}

