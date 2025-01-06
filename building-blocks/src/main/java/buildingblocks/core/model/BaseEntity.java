package buildingblocks.core.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
@FilterDef(name = "softDeleteFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "softDeleteFilter", condition = "is_deleted = :isDeleted")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity<T> {
    @Id
    private T id;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime lastModified;
    private Long lastModifiedBy;
    private Boolean isDeleted;
    @Version
    private Long version;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        BaseEntity<?> that = (BaseEntity<?>) o;
        return Objects.equals(id, that.id) && Objects.equals(createdAt, that.createdAt) && Objects.equals(createdBy, that.createdBy) && Objects.equals(lastModified, that.lastModified) && Objects.equals(lastModifiedBy, that.lastModifiedBy) && Objects.equals(isDeleted, that.isDeleted) && Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(createdAt);
        result = 31 * result + Objects.hashCode(createdBy);
        result = 31 * result + Objects.hashCode(lastModified);
        result = 31 * result + Objects.hashCode(lastModifiedBy);
        result = 31 * result + Objects.hashCode(isDeleted);
        result = 31 * result + Objects.hashCode(version);
        return result;
    }
}