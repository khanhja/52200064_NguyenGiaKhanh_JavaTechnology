package ex02;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@WebServlet("/image1")
public class ImageServlet1 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String imagePath = getServletContext().getRealPath("/WEB-INF/classes/image1.png");

        // Check if the image does exist
        try (InputStream imgStream = new FileInputStream(imagePath)) {
            response.setContentType("image/png");
            // Import image to response
            OutputStream out = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = imgStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.flush();
        } catch (IOException e) {
            // Image does not exist, reply a page
            response.setContentType("text/html");
            response.getWriter().println("<html><body><h1>Image not found !</h1></body></html>");
        }
    }
}
