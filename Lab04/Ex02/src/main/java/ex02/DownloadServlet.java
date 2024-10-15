package ex02;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = request.getParameter("file");
        String speedParam = request.getParameter("speed");

        // Check if the query string is not passed
        if (fileName == null || fileName.isEmpty()) {
            response.setContentType("text/html");
            response.getWriter().write("<html><body>");
            response.getWriter().write("<h1>File not found (You need to specify a file)</h1>");
            response.getWriter().write("</body></html>");
            return;
        }

        // Path to repository
        String filePath = getServletContext().getRealPath("/WEB-INF/classes/repository/" + fileName);
        File file = new File(filePath);

        // Check if the repository has the "file"
        if (!file.exists()) {
            response.setContentType("text/html");
            response.getWriter().write("<html><body>");
            response.getWriter().write("<h1>The file that you need is not for providing now</h1>");
            response.getWriter().write("</body></html>");
            return;
        }

        // Parse speed from the query parameter (KB/s)
        int speed = 0;
        if (speedParam != null) {
            try {
                speed = Integer.parseInt(speedParam); // Speed in KB/s
            } catch (NumberFormatException e) {
                speed = 0; // No speed limit if not correctly specified
            }
        }

        // Convert speed to bytes per second (KB/s * 1024)
        int bytesPerSecond = speed * 1024;

        // Download it
        response.setContentType(getServletContext().getMimeType(file.getName()));
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

        FileInputStream fileInputStream = new FileInputStream(file);
        OutputStream outputStream = response.getOutputStream();

        byte[] buffer = new byte[1024];
        int bytesRead;
        long totalBytesSent = 0;

        // If speed is specified, limit the download speed
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
            totalBytesSent += bytesRead;

            if (speed > 0 && totalBytesSent >= bytesPerSecond) {
                totalBytesSent = 0;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        fileInputStream.close();
        outputStream.flush();
    }
}