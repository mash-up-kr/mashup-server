package kr.mashup.branding.domain.storage;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStorage is a Querydsl query type for Storage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStorage extends EntityPathBase<Storage> {

    private static final long serialVersionUID = -452143922L;

    public static final QStorage storage = new QStorage("storage");

    public final kr.mashup.branding.domain.QBaseEntity _super = new kr.mashup.branding.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath keyString = createString("keyString");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final MapPath<String, Object, SimplePath<Object>> valueMap = this.<String, Object, SimplePath<Object>>createMap("valueMap", String.class, Object.class, SimplePath.class);

    public QStorage(String variable) {
        super(Storage.class, forVariable(variable));
    }

    public QStorage(Path<? extends Storage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStorage(PathMetadata metadata) {
        super(Storage.class, metadata);
    }

}

