package ex04.Entity;

import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;

@Component("plainTextWriter")
public class PlainTextWriter implements TextWriter {
    @Override
    public void write(String fileName, String text) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileName);
            fileWriter.write(text);
            System.out.println("Successfully written to the file.");
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file.");
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    System.err.println("Failed to close the file writer.");
                    e.printStackTrace();
                }
            }
        }
    }
}