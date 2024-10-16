package BUS;

import DAO.UserDAO;
import ORM.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
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

        // Client is not logged-in
        // Track cookies
        Cookie[] cookies = request.getCookies();
        String username = null;
        String password = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    username = cookie.getValue();
                } else if (cookie.getName().equals("password")) {
                    password = cookie.getValue();
                }
            }
        }

        if (username != null)
            request.setAttribute("username", username);
        if (password != null)
            request.setAttribute("password", password);

        // Render login page (login.jsp)
        RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");

        // Select account by username (email) input
        UserDAO userDAO = new UserDAO();
        User user = userDAO.get(username);

        if (user != null) {
            // Validate account
            if (user.getPassword().equals(password)) {
                HttpSession session = request.getSession();
                session.setAttribute("username", username); // Lưu username vào session

                // Remember me
                if (rememberMe != null)
                    if (rememberMe.equals("on")) {
                        Cookie usernameCookie = new Cookie("username", username);
                        Cookie passwordCookie = new Cookie("password", password);
                        usernameCookie.setMaxAge(30 * 24 * 60 * 60); // 30 days
                        passwordCookie.setMaxAge(30 * 24 * 60 * 60);
                        response.addCookie(usernameCookie);
                        response.addCookie(passwordCookie);
                    }

                // Login successfully: Redirect to homepage (index.jsp)
                response.sendRedirect("productmanage");
            }
        }

        // Login failed: return to login.jsp
        if (user == null || !user.getPassword().equals(password)) {
            request.setAttribute("errorMessage", "Invalid username or password.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        }

        userDAO.close();
    }

}
