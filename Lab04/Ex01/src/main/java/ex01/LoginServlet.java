package ex01;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private HashMap<String, String> accounts;

    @Override
    public void init() throws ServletException {
        accounts = new HashMap<>();
        accounts.put("admin", "123");
        accounts.put("user1", "123");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        // Check if user logged-in by session
        if (username != null) {
            response.setContentType("text/html");
            // Reply to client that they logged-in before
            String htmlContent = generateHtmlContent("<h1>You logged-in before !</h1>");
            response.getWriter().write(htmlContent);
        } else
            // Navigate client to the login page
            request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        response.setContentType("text/html");

        // Check account
        if (accounts.containsKey(username) && accounts.get(username).equals(password)) {
            // Mark logged-in to session
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            // Reply a page
            String htmlContent = generateHtmlContent("<h1>Login successful!</h1>");
            response.getWriter().write(htmlContent);
        } else {
            request.setAttribute("errorMessage", "Invalid username or password.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    // Generate a page for notification: login successful
    private String generateHtmlContent(String bodyContent) {
        StringBuilder html = new StringBuilder();
        html.append("<html>")
                .append("<head><title>Login Result</title>")
                .append("<style>")
                .append("body { background-color: white; color: black; font-family: Arial, sans-serif; }")
                .append("h1 { text-align: center; }")
                .append("</style>")
                .append("</head>")
                .append("<body>")
                .append(bodyContent)  // Content
                .append("</body>")
                .append("</html>");

        return html.toString();
    }
}