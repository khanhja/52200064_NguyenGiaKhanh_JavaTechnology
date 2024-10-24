package ex03;

import ex03.Entity.TextEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        TextEditor editor = context.getBean(TextEditor.class);

        // Use PdfTextEditor
        editor.input("Hello, Dependency Injection with Spring!");
        editor.save("output.pdf");
    }
}
