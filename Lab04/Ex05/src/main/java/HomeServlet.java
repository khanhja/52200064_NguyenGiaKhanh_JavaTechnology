import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String page = request.getParameter("page");

        if (page != null)
            switch (page) {
                case "about":
                    forwardToPage(request, response, "about.jsp");
                    break;
                case "help":
                    forwardToPage(request, response, "help.jsp");
                    break;
                case "contact":
                    forwardToPage(request, response, "contact.jsp");
                    break;
                default:
                    homepage(response);
                    break;
            }
        else
            homepage(response);
    }

    private void forwardToPage(HttpServletRequest request, HttpServletResponse response, String jspPage)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(jspPage);
        dispatcher.forward(request, response);
    }

    private void homepage(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h1>Welcome to my website</h1>");
        response.getWriter().println("</body></html>");
    }
}