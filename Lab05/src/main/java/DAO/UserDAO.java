package DAO;

import ORM.User;
import org.hibernate.Session;

public class UserDAO {
    private Session session;

    public UserDAO() {
        this.session = HibernateUtils.getSessionFactory().openSession();
    }

    public void close() {
        session.close();
    }

    // Get User by Email (ID)
    public User get(String email) {
        return session.get(User.class, email);
    }

    // Create new User
    public boolean add(User user) {
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
    }

}
