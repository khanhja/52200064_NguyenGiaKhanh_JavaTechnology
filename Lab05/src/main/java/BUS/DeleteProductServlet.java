package BUS;

import DAO.ProductDAO;
import DAO.UserDAO;
import ORM.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/deleteProduct")
public class DeleteProductServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productName = request.getParameter("productName");
        ProductDAO productDAO = new ProductDAO();

        // Check if productName does exist in database
        if (productName != null && !productName.trim().isEmpty()) {
            boolean isDeleted = productDAO.delete(productName);

            if (isDeleted) {
                request.setAttribute("successMessage", "Sản phẩm đã được xóa thành công.");
            } else {
                request.setAttribute("errorMessage", "Có lỗi xảy ra khi xóa sản phẩm.");
            }
        } else {
            request.setAttribute("errorMessage", "ID sản phẩm không hợp lệ.");
        }

        // Prepare information: User's full name, list of products
        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("username");
        UserDAO userDAO = new UserDAO();
        String fullName = userDAO.get(username).getFullName();
        List<Product> products = productDAO.getAll();

        // Return information
        request.setAttribute("products", products);
        request.setAttribute("fullname", fullName);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
