import java.sql.*;
import java.util.ArrayList;

public class TestConnectionAndQuery {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/demodb?useSSL=false&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "1234567890";

        Connection conn = DriverManager.getConnection(url, username, password);
        System.out.println("Connected to database");

        // Execute statement: CREATE
//        String sql = "CREATE TABLE Student(sID varchar(8) primary key, name nvarchar(50), gender varchar(5))";
//        Statement stm = (Statement) conn.createStatement();
//        stm.executeUpdate(sql);
//        stm.close();

        // Execute statement: INSERT
        String sql = "INSERT INTO Student VALUES (?, ?, ?)";
        PreparedStatement ptm = (PreparedStatement) conn.prepareStatement(sql);
        ptm.setString(1, "52200093");
        ptm.setString(2, "Nguyễn Trà Anh Khoa");
        ptm.setString(3, "Male");

        if (ptm.executeUpdate() > 0)
            System.out.println("INSERTED a student");

        // Execute statement: SELECT
        sql = "SELECT * FROM Student";
        Statement stm = (Statement) conn.createStatement();
        ResultSet data = stm.executeQuery(sql);
        ArrayList<Student> studentList = new ArrayList<Student>();

        while(data.next()) {
            String sID = data.getString("sID");
            String name = data.getString("name");
            String gender = data.getString("gender");
            studentList.add(new Student(sID, name, gender));
        }

        studentList.forEach(System.out::println);
        conn.close();
    }
}
