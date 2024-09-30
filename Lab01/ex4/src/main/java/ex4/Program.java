package ex4;
import org.apache.commons.io.FileUtils;
import org.apache.commons.validator.routines.UrlValidator;
import java.io.IOException;
import java.net.URL;
import java.io.File;

public class Program {
    public static void main(String[] args) {
        // No URL
        if (args.length == 0)
            System.out.print("Please specify an URL to a file");
        else {
            // Invalid URL
            UrlValidator uv = new UrlValidator();
            if (!uv.isValid(args[0]))
                System.out.print("This is not a valid URL");
            // Valid URL
            else
                try {
                    URL u = new URL(args[0]);
                    File f = new File(getFileName(args[0]));
                    FileUtils.copyURLToFile(u, f);
                } catch (IOException e) {
                    System.out.print("There exists an error: " + e.getMessage());
                }
        }
    }

    // Extract original file name
    public static String getFileName(String url) {
        return url.substring(url.lastIndexOf('/')+1);
    }
}
