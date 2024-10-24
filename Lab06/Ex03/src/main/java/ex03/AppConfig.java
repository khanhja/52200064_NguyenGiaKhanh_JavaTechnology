package ex03;

import ex03.Entity.PdfTextWriter;
import ex03.Entity.TextEditor;
import ex03.Entity.TextWriter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public TextWriter pdfTextWriter() {
        return new PdfTextWriter();
    }

    @Bean
    public TextEditor textEditor() {
        return new TextEditor(pdfTextWriter());
    }
}
