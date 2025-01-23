package buildingblocks.jpa;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateGlobalFilter {
    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    public void enableSoftDeleteFilter() {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("softDeleteFilter");
        filter.setParameter("isDeleted", false);
    }
}