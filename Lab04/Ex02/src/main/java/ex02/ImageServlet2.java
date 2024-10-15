package ex02;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/image2")
public class ImageServlet2 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        // In the first access: action = none -> display "image2" firstly
        if ("download".equals(action))
            download(response);
        else
            display(response);
    }

    private void display(HttpServletResponse response) throws IOException {
        String imageURL = "/image2?action=download";
        String imageSrc = "/image2?action=download";

        // Reply a page for display "image2"
        response.setContentType("text/html");
        response.getWriter().write("<html>");
        response.getWriter().write("<head><title>Image Display</title></head>");
        response.getWriter().write("<style>");
        response.getWriter().write("body, html { margin: 0; padding: 0; height: 100%; overflow: hidden; }"); // Thiết lập chiều cao cho body
        response.getWriter().write("img { width: 100%; height: 100%; object-fit: cover; }"); // Đặt ảnh chiếm toàn bộ chiều rộng và chiều cao
        response.getWriter().write("</style>");
        response.getWriter().write("</head>");
        response.getWriter().write("<body>");
        response.getWriter().write("<h1 style='display: none;'>Image Display</h1>"); // Ẩn tiêu đề nếu không cần

        response.getWriter().write("<img src='" + imageSrc + "' alt='Image'>");

        // Configure a JavaScript for handling download "image2" automatically
        response.getWriter().write("<script>");
        // After "onLoad" -> redirect client to the URL including "download" action
        response.getWriter().write("window.onload = function() {");
        response.getWriter().write("    var link = document.createElement('a');");
        response.getWriter().write("    link.href = '" + imageURL + "';");
        response.getWriter().write("    link.download = 'image2.jpg';");
        response.getWriter().write("    link.click();");
        response.getWriter().write("};");
        response.getWriter().write("</script>");

        response.getWriter().write("</body>");
        response.getWriter().write("</html>");
    }

    // Handle download file
    private void download(HttpServletResponse response) throws IOException {
        String imagePath = getServletContext().getRealPath("/WEB-INF/classes/image2.jpg");
        response.setContentType("image/jpeg");
        response.setHeader("Content-Disposition", "attachment; filename=\"image2.jpg\"");

        File file = new File(imagePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        OutputStream outputStream = response.getOutputStream();

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        fileInputStream.close();
        outputStream.flush();
    }
}
