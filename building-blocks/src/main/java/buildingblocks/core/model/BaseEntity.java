package buildingblocks.core.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

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
    @CreatedDate
    private LocalDateTime createdAt;
    @CreatedBy
    private Long createdBy;
    @LastModifiedDate
    private LocalDateTime lastModified;
    @LastModifiedBy
    private Long lastModifiedBy;
    @Version
    private Long version;
    private Boolean isDeleted;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        BaseEntity<?> that = (BaseEntity<?>) o;
        return Objects.equals(id, that.id) && Objects.equals(createdAt, that.createdAt) && Objects.equals(createdBy, that.createdBy) && Objects.equals(lastModified, that.lastModified) && Objects.equals(lastModifiedBy, that.lastModifiedBy) && Objects.equals(version, that.version) && Objects.equals(isDeleted, that.isDeleted);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(createdAt);
        result = 31 * result + Objects.hashCode(createdBy);
        result = 31 * result + Objects.hashCode(lastModified);
        result = 31 * result + Objects.hashCode(lastModifiedBy);
        result = 31 * result + Objects.hashCode(version);
        result = 31 * result + Objects.hashCode(isDeleted);
        return result;
    }
}