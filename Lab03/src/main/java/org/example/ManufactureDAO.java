package org.example;

import org.hibernate.Session;
import java.util.List;

public class ManufactureDAO {
    private Session session;

    public ManufactureDAO() {
        this.session = HibernateUtils.getSessionFactory().openSession();
    }

    public void close() {
        session.close();
    }

    // ID format: MF<number>, for example: MF001, MF710, ...
    public String generateID() {
        String nearestID = session.createQuery("SELECT MAX(id) FROM Manufacture", String.class).uniqueResult();
        if (nearestID == null)
            return "MF001";
        else {
            int num_part = Integer.parseInt(nearestID.substring(2));
            return "MF" + String.format("%03d", num_part+1);
        }
    }

    public boolean add(Manufacture mf) {
        try {
            mf.setId(generateID());
            session.beginTransaction();
            session.save(mf);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
    }

    public Manufacture get(String id) {
        return session.get(Manufacture.class, id);
    }

    public Manufacture createIfNotExist() {
        Manufacture mf = session.createQuery(
                "FROM Manufacture", Manufacture.class)
                .setMaxResults(1)
                .uniqueResult();

        if (mf == null) {
            add(new Manufacture());
            mf = session.get(Manufacture.class, "MF001");
        }
        return mf;
    }

    public List<Manufacture> getAll() {
        return session.createQuery("FROM Manufacture").list();
    }

    public boolean remove(String id) {
        try {
            Manufacture mf = this.get(id);
            if (mf != null) {
                session.beginTransaction();
                session.delete(mf);
                session.getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
    }

    public boolean remove(Manufacture mf) {
        try {
            if (mf != null && this.get(mf.getId()) != null) {
                session.beginTransaction();
                session.delete(mf);
                session.getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
    }

    public boolean update(Manufacture mf) {
        try {
            if (mf != null && this.get(mf.getId()) != null) {
                session.beginTransaction();
                session.update(mf);
                session.getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
    }

    // check if all manufactures have more than 100 employees
    public boolean haveMoreThan100Emp() {
        long count = 0;
        count = session.createQuery(
                "SELECT COUNT(*) FROM Manufacture WHERE employee <= 100", long.class)
                .uniqueResult();
        return count == 0;
    }

    // sum of all employees of the manufactures
    public long sumOfAllEmployees() {
        long sum = session.createQuery(
                "SELECT SUM(employee) FROM Manufacture", long.class)
                .uniqueResult();
        return sum;
    }

    // get the last manufacturer in the list of manufacturers that based in the US
    public Manufacture lastManufactureBasedInUS() throws InvalidOperationException {
        List<Manufacture> mf = session.createQuery(
                "FROM Manufacture WHERE location = 'US'", Manufacture.class)
                .list();
        if (mf.isEmpty())
            throw new InvalidOperationException("No manufacturers found in the US.");

        return mf.get(mf.size()-1);
    }
}
