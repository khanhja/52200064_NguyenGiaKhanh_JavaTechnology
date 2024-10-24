package ex03.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

public class TextEditor {
    private String text;
    private final TextWriter writer;

    public TextEditor(TextWriter writer) {
        this.writer = writer;
    }

    public void input(String text) {
        this.text = text;
    }

    public void save(String fileName) {
        this.writer.write(fileName, this.text);
    }
}
