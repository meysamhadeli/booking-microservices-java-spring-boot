package buildingblocks.jpa;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateGlobalFilter {
    private final SessionFactory sessionFactory;

    public HibernateGlobalFilter(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void enableSoftDeleteFilter() {
        Session session = sessionFactory.getCurrentSession();
        Filter filter = session.enableFilter("softDeleteFilter");
        filter.setParameter("isDeleted", false);
    }
}
