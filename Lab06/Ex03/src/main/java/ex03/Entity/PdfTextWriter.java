package ex03.Entity;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

public class PdfTextWriter implements TextWriter {
    @Override
    public void write(String fileName, String text) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();
            document.add(new com.itextpdf.text.Paragraph(text));
            System.out.println("Successfully written to the PDF file.");
        } catch (DocumentException | IOException e) {
            System.err.println("An error occurred while writing to the PDF file.");
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}