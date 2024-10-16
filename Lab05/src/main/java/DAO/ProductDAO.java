package DAO;

import ORM.Product;
import org.hibernate.Session;

import java.util.List;

public class ProductDAO {
    private Session session;

    public ProductDAO() {
        this.session = HibernateUtils.getSessionFactory().openSession();
    }

    public void close() {
        session.close();
    }

    // Get Product by productName (ID)
    public Product get(String productName) {
        return session.get(Product.class, productName);
    }

    // Create new Product
    public boolean add(Product product) {
        try {
            session.beginTransaction();
            session.save(product);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
    }

    // Get all products
    public List<Product> getAll() {
        return session.createQuery("FROM Product").list();
    }

    // Delete a product by productName (ID)
    public boolean delete(String productName) {
        try {
            Product product = this.get(productName);
            if (product != null) {
                session.beginTransaction();
                session.delete(product);
                session.getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
    }
}
