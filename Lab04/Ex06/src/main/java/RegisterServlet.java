import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/register.html");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get data
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String birthday = request.getParameter("birthday");
        String birthtime = request.getParameter("birthtime");
        String gender = request.getParameter("gender");
        String country = request.getParameter("country");
        String[] favoriteIDE = request.getParameterValues("favorite_ide[]");
        String toeicScore = request.getParameter("toeic");
        String message = request.getParameter("message");

        List<String> missingFields = new ArrayList<>();

        // Check missing information
        if (name == null || name.isEmpty())
            missingFields.add("Họ tên");

        if (email == null || email.isEmpty())
            missingFields.add("Email");

        if (birthday == null || birthday.isEmpty())
            missingFields.add("Ngày sinh");

        if (birthtime == null || birthtime.isEmpty())
            missingFields.add("Giờ sinh");

        if (gender == null || gender.isEmpty())
            missingFields.add("Giới tính");

        if (country == null || country.isEmpty())
            missingFields.add("Quốc gia");

        if (toeicScore == null || toeicScore.isEmpty())
            missingFields.add("Điểm TOEIC");

        if (message == null || message.isEmpty())
            missingFields.add("Giới thiệu bản thân");

        response.setContentType("text/html;charset=UTF-8");

        if (!missingFields.isEmpty()) {
            // Reply a page to notice missing information
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("<html><head><style>");
            errorMessage.append(".error { color: red; }");
            errorMessage.append("</style></head><body>");
            errorMessage.append("<h3>Các trường thông tin bị thiếu:</h3><ul class='error'>");

            for (String field : missingFields) {
                errorMessage.append("<li>").append(field).append("</li>");
            }
            errorMessage.append("</ul>");
            errorMessage.append("<a href='register.html'>Quay lại</a>");
            errorMessage.append("</body></html>");

            response.getWriter().write(errorMessage.toString());
        } else {
            StringBuilder htmlResponse = new StringBuilder();
            htmlResponse.append("<html><head><style>");
            htmlResponse.append("table {border-collapse: collapse; width: 100%;}");
            htmlResponse.append("table, td, th {border: 1px solid black; padding: 8px; text-align: left;}");
            htmlResponse.append("td {padding: 10px;}");
            htmlResponse.append("td:nth-child(1) {font-weight: bold;}");
            htmlResponse.append("</style></head><body>");
            htmlResponse.append("<table>");
            htmlResponse.append("<tr><td>Họ tên</td><td>").append(name).append("</td></tr>");
            htmlResponse.append("<tr><td>Email</td><td>").append(email).append("</td></tr>");
            htmlResponse.append("<tr><td>Ngày sinh</td><td>").append(birthday).append("</td></tr>");
            htmlResponse.append("<tr><td>Giờ sinh</td><td>").append(birthtime).append("</td></tr>");
            htmlResponse.append("<tr><td>Giới tính</td><td>").append(gender).append("</td></tr>");
            htmlResponse.append("<tr><td>Quốc gia</td><td>").append(country).append("</td></tr>");
            htmlResponse.append("<tr><td>IDE Yêu thích</td><td>");
            if (favoriteIDE != null) {
                for (String ide : favoriteIDE) {
                    htmlResponse.append(ide).append("<br>");
                }
            } else {
                htmlResponse.append("None");
            }
            htmlResponse.append("</td></tr>");
            htmlResponse.append("<tr><td>Điểm TOEIC</td><td>").append(toeicScore).append("</td></tr>");
            htmlResponse.append("<tr><td>Giới thiệu bản thân</td><td>").append(message).append("</td></tr>");
            htmlResponse.append("</table>");
            htmlResponse.append("</body></html>");

            response.getWriter().write(htmlResponse.toString());
        }
    }
}