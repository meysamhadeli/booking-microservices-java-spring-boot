package buildingblocks.jpa;

import buildingblocks.core.model.BaseEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import org.springframework.data.domain.AuditorAware;

import java.time.LocalDateTime;

public class AuditingEntityListener {

    private AuditorAware<Long> auditorAware;

    // Default constructor for Hibernate
    public AuditingEntityListener() {}

    public AuditingEntityListener(AuditorAware<Long> auditorAware) {
        this.auditorAware = auditorAware;
    }

    @PrePersist
    public void setCreatedFields(BaseEntity<?> entity) {
        Long userId = getUserId();

        entity.setCreatedAt(LocalDateTime.now());
        entity.setCreatedBy(userId);
    }

    @PreUpdate
    public void setModifiedFields(BaseEntity<?> entity) {
        Long userId = getUserId();

        entity.setLastModifiedBy(userId);
        entity.setLastModified(LocalDateTime.now());
    }

    @PreRemove
    public void setRemovedFields(BaseEntity<?> entity) {
        Long userId = getUserId();

        entity.setLastModifiedBy(userId);
        entity.setLastModified(LocalDateTime.now());
        entity.setIsDeleted(true);
    }

    private Long getUserId() {
        return auditorAware != null ? auditorAware.getCurrentAuditor().orElse(1L) : 1L;
    }
}
