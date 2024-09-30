import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements Repository<Product, String> {
    static String sql;
    static Connection conn;
    static Statement stm;
    static PreparedStatement pstm;
    static ResultSet data;

    public ProductDAO(String url, String username, String password) {
        try {
            conn = DriverManager.getConnection(url, username, password);
            Statement stm = conn.createStatement();

            // CREATE ProductManagement DB
            String sql = "CREATE DATABASE IF NOT EXISTS " + "ProductManagement";
            stm.executeUpdate(sql);

            // Specify DB
            sql = "USE " + "ProductManagement";
            stm.executeUpdate(sql);

            // CREATE new Product TABLE
            sql = "DROP TABLE IF EXISTS Product";
            stm.executeUpdate(sql);

            sql = "CREATE TABLE Product(" +
                    "pID VARCHAR(5), " +
                    "name NVARCHAR(50), " +
                    "price DOUBLE, " +
                    "quantity INT, " +
                    "description NVARCHAR(50)" +
                    ")";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getNewProductID() throws SQLException {
        String sql = "SELECT MAX(pID) AS maxPID FROM Product";
        Statement stm = conn.createStatement();
        ResultSet data = stm.executeQuery(sql);

        String maxPID = null;
        if (data.next())
            maxPID = data.getString("maxPID");

        if (maxPID == null)
            return "PD001";
        else {
            int newID = Integer.parseInt(maxPID.substring(2)) + 1;
            return String.format("PD%03d", newID);
        }
    }


    @Override
    public String add(Product item) {
        sql = "INSERT INTO Product VALUES(?, ?, ?, ?, ?)";
        try {
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            String pID = getNewProductID();

            pstm.setString(1, pID);
            pstm.setString(2, item.getName());
            pstm.setDouble(3, item.getPrice());
            pstm.setInt(4, item.getQuantity());
            pstm.setString(5, item.getDescription());

            if (pstm.executeUpdate() > 0)
                return pID;
            else
                return null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Product> readAll() {
        sql = "SELECT * FROM Product";
        try {
            stm = (Statement) conn.createStatement();
            data = stm.executeQuery(sql);
            ArrayList<Product> productList = new ArrayList<>();
            while (data.next()) {
                String pID = data.getString("pID");
                String name = data.getString("name");
                double price = data.getDouble("price");
                int quantity = data.getInt("quantity");
                String description = data.getString("description");
                productList.add(new Product(pID, name, price, quantity, description));
            }

            return productList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Product read(String id) {
        sql = "SELECT * FROM Product WHERE pID = ?";
        try {
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.setString(1, id);
            data = pstm.executeQuery();

            if (data.next()) {
                String pID = data.getString("pID");
                String name = data.getString("name");
                double price = data.getDouble("price");
                int quantity = data.getInt("quantity");
                String description = data.getString("description");
                return new Product(pID, name, price, quantity, description);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean update(Product item) {
        sql = "UPDATE Product SET name = ?, price = ?, quantity = ?, description = ? WHERE pID = ?";
        try {
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.setString(1, item.getName());
            pstm.setDouble(2, item.getPrice());
            pstm.setInt(3, item.getQuantity());
            pstm.setString(4, item.getDescription());
            pstm.setString(5, item.getpID());

            if (pstm.executeUpdate() > 0)
                return true;
            else
                return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id) {
        sql = "DELETE FROM Product WHERE pID = ?";
        try {
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.setString(1, id);

            if (pstm.executeUpdate() > 0)
                return true;
            else
                return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
