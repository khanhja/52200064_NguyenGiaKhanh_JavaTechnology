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

@WebServlet("/productmanage")
public class ProductManagementServlet extends HttpServlet {
    private List<Product> products;
    private String fullName;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Track session
        HttpSession session = request.getSession(false); // Do not create new session if existing
        String username = (session != null) ? (String) session.getAttribute("username") : null;

        // Client is not logged-in
        if (username == null) {
            response.sendRedirect("/login"); // Redirect to LoginServlet
            return;
        }

        // Client is logged-in
        // Render homepage (index.jsp)
        UserDAO userDAO = new UserDAO();
        fullName = userDAO.get(username).getFullName();

        ProductDAO productDAO = new ProductDAO();
        products = productDAO.getAll();
        request.setAttribute("products", products);
        request.setAttribute("fullname", fullName);
        request.getRequestDispatcher("/index.jsp").forward(request, response);

        productDAO.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get data
        String productName = request.getParameter("name");
        String productPriceStr = request.getParameter("price");

        // Validate input
        String errorMessage = null;
        if (productName == null || productName.trim().isEmpty())
            errorMessage = "Vui lòng nhập tên sản phẩm.";

        int productPrice = 0;
        if (productPriceStr == null || productPriceStr.trim().isEmpty())
            errorMessage = "Vui lòng nhập giá sản phẩm.";
        else
            try {
                productPrice = Integer.parseInt(productPriceStr);
                if (productPrice <= 0)
                    errorMessage = "Giá sản phẩm phải lớn hơn 0.";
            } catch (NumberFormatException e) {
                errorMessage = "Giá sản phẩm không hợp lệ.";
            }

        ProductDAO productDAO = new ProductDAO();
        Product product = new Product(productName, productPrice);
        if (productDAO.get(productName) != null)
            errorMessage = "Sản phẩm đã tồn tại trong kho.";

        // Invalid input: go to homepage and display error
        if (errorMessage != null) {
            request.setAttribute("fullname", fullName);
            request.setAttribute("errorMessage", errorMessage);
            request.setAttribute("products", products);
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

        // Data is valid: Insert to database
        productDAO.add(product);

        // Back to homepage
        response.sendRedirect(request.getContextPath() + "/productmanage");
        productDAO.close();
    }
}
