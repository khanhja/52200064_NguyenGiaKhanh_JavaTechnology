package org.example;

import org.hibernate.Session;
import java.util.List;

public class PhoneDAO {
    private Session session;

    public PhoneDAO() {
        this.session = HibernateUtils.getSessionFactory().openSession();
    }

    public void close() {
        session.close();
    }

    // ID format: P<number>, for example: P001, P710, ...
    public String generateID() {
        String nearestID = session.createQuery(
                "SELECT MAX(id) FROM Phone", String.class)
                .uniqueResult();
        if (nearestID == null)
            return "P001";
        else {
            int num_part = Integer.parseInt(nearestID.substring(1));
            return "P" + String.format("%03d", num_part+1);
        }
    }

    public boolean add(Phone p) {
        try {
            p.setId(generateID());
            session.beginTransaction();
            session.save(p);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
    }

    public Phone get(String id) {
        return session.get(Phone.class, id);
    }

    public List<Phone> getAll() {
        return session.createQuery("FROM Phone").list();
    }

    public boolean remove(String id) {
        try {
            Phone p = this.get(id);
            if (p != null) {
                session.beginTransaction();
                session.delete(p);
                session.getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
    }

    public boolean remove(Phone p) {
        try {
            if (p != null && this.get(p.getId()) != null) {
                session.beginTransaction();
                session.delete(p);
                session.getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
    }

    public boolean update(Phone p) {
        try {
            if (p != null && this.get(p.getId()) != null) {
                session.beginTransaction();
                session.update(p);
                session.getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
    }

    // get the highest selling price phone
    public Phone getHighestPrice() {
        int price = session.createQuery(
                "SELECT MAX(price) FROM Phone", int.class)
                .uniqueResult();

        Phone p = session.createQuery(
                "FROM Phone WHERE price = :price", Phone.class)
                .setParameter("price", price)
                .setMaxResults(1)
                .uniqueResult();
        return p;
    }

    // get phone list sorted by country name
    public List<Phone> listSortedByCountry() {
        List<Phone> phones = session.createQuery(
                "FROM Phone ORDER BY country ASC, price DESC", Phone.class)
                .list();
        return phones;
    }

    // check if there's a phone priced above 50 million VNĐ
    public boolean above50Million() {
        Phone p =  session.createQuery(
                "FROM Phone WHERE price > 50000000", Phone.class)
                .setMaxResults(1)
                .uniqueResult();
        return p != null;
    }

    // phone has the color 'Pink' and costs over 15 million
    public Phone getPinkAndOver15Million() {
        Phone p = session.createQuery(
                "FROM Phone WHERE color = 'Pink' AND price > 15000000", Phone.class)
                .setMaxResults(1)
                .uniqueResult();
        return p;
    }
}
