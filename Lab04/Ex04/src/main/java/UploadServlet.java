import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("txt", "doc", "docx", "img", "pdf", "rar", "zip");


    // Note: The file that has been uploaded by client will be stored in folder below
    // .\apache-tomcat-<version>\bin\target\classes\repository\<file name>
    // For example: C:\apache-tomcat-10.1.30\apache-tomcat-10.1.30\bin\target\classes\repository\test.docx
    // You can follow the Server log to looking for the path stored.


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = request.getParameter("file");

        // The first time that client get access to /upload will present the uploading page (upload.html)
        // In this time, the file parameter does not exist
        if (fileName == null)
            request.getRequestDispatcher("/upload.html").forward(request, response);

        // After uploading, the server will generate a link for downloading the uploaded file
        // For example: You can download it directly from this link: Click here to download
        // If the client click the link, that will make a call to doGet()
        // doGet() check the file parameter again, in this time the fileName does exist.
        else {
            // Handle downloading
            String uploadDirectory = System.getProperty("user.dir") + "/target/classes/repository/";
            File file = new File(uploadDirectory + fileName);
            if (!file.exists()) {
                response.getWriter().println("File not found.");
                return;
            }

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + file.getName() + "\"");

            try (FileInputStream inStream = new FileInputStream(file);
                 OutputStream outStream = response.getOutputStream()) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = request.getParameter("name");
        Part filePart = request.getPart("document");

        if (filePart == null || filePart.getSize() == 0) {
            response.getWriter().println("No file was uploaded.");
            return;
        }

        String uploadDirectory = System.getProperty("user.dir") + "/target/classes/repository/";
        File uploadDir = new File(uploadDirectory);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String fileExtension = getFileExtension(filePart.getSubmittedFileName());
        if (!ALLOWED_EXTENSIONS.contains(fileExtension)) {
            response.getWriter().println("Unsupported file extension");
            return;
        }

        File file = new File(uploadDirectory + fileName + "." + fileExtension);

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        String override = request.getParameter("override");
        if (file.exists()) {
            if (override == null) {
                response.getWriter().println("<html><body><p>File already exists.</p></body></html>");
                return;
            }

            response.getWriter().println("<html><body>");
            response.getWriter().println("<p>File has been overridden. You can download it directly from this link: ");
            response.getWriter().println("<a href='" + request.getContextPath() + "/upload?file=" + file.getName() + "'>Click here to download</a></p>");
            response.getWriter().println("</body></html>");
            return;
        }

        try (InputStream fileContent = filePart.getInputStream();
             FileOutputStream fos = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileContent.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            response.getWriter().println("<html><body>");
            response.getWriter().println("<p>File uploaded successfully. You can download it directly from this link: ");
            response.getWriter().println("<a href='" + request.getContextPath() + "/upload?file=" + file.getName() + "'>Click here to download</a></p>");
            response.getWriter().println("</body></html>");
        } catch (IOException e) {
            response.getWriter().println("<html><body>");
            response.getWriter().println("<p>File upload failed: " + e.getMessage() + "</p>");
            response.getWriter().println("</body></html>");
            e.printStackTrace();
        }
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }
}