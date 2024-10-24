package ex05;

import ex05.Entity.Person;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        Person info = context.getBean(Person.class);

        System.out.println(info);
    }
}