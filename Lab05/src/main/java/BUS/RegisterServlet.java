package BUS;

import DAO.UserDAO;
import ORM.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Track session
        HttpSession session = request.getSession(false);
        String usernameFromSession = (session != null) ? (String) session.getAttribute("username") : null;

        // Client is logged-in, redirect to homepage (index.jsp)
        if (usernameFromSession != null) {
            response.sendRedirect(request.getContextPath() + "/productmanage");
            return;
        }

        // Client is NOT logged-in, redirect to account registration (register.jsp)
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get data
        String fullName = request.getParameter("fullname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("password-confirm");

        String errorMessage = null;

        // Check confirm password
        if (!password.equals(passwordConfirm))
            errorMessage = "Passwords do not match!";

        // Check if email does exist
        UserDAO userDAO = new UserDAO();
        if (userDAO.get(email) != null)
            errorMessage = "Email already registered!";

        // Invalid input: /register.jsp again and send error message to client
        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            request.setAttribute("fullname", fullName);
            request.setAttribute("email", email);
            request.getRequestDispatcher("/register.jsp").forward(request, response);

            userDAO.close();
            return;
        }

        // Valid input: create new account
        User user = new User(email, fullName,password);

        // Add successfully: redirect to /login.jsp with autofilled data
        if (userDAO.add(user)) {
            request.setAttribute("username", email);
            request.setAttribute("password", password);

//            response.sendRedirect(request.getContextPath() + "/login");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        }

        userDAO.close();
    }
}