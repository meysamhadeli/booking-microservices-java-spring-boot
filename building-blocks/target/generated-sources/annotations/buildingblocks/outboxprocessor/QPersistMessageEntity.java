package buildingblocks.outboxprocessor;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPersistMessageEntity is a Querydsl query type for PersistMessageEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPersistMessageEntity extends EntityPathBase<PersistMessageEntity> {

    private static final long serialVersionUID = 1928200323L;

    public static final QPersistMessageEntity persistMessageEntity = new QPersistMessageEntity("persistMessageEntity");

    public final DateTimePath<java.time.LocalDateTime> created = createDateTime("created", java.time.LocalDateTime.class);

    public final StringPath data = createString("data");

    public final StringPath dataType = createString("dataType");

    public final EnumPath<MessageDeliveryType> deliveryType = createEnum("deliveryType", MessageDeliveryType.class);

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final EnumPath<MessageStatus> messageStatus = createEnum("messageStatus", MessageStatus.class);

    public final NumberPath<Integer> retryCount = createNumber("retryCount", Integer.class);

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QPersistMessageEntity(String variable) {
        super(PersistMessageEntity.class, forVariable(variable));
    }

    public QPersistMessageEntity(Path<? extends PersistMessageEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPersistMessageEntity(PathMetadata metadata) {
        super(PersistMessageEntity.class, metadata);
    }

}

