package ex01;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Program {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("appConfig.xml");

        // Load first and second beans (prototype)
        Product product1 = (Product) context.getBean("product1");
        Product product2 = (Product) context.getBean("product2");

        // Load third bean (singleton)
        Product product3a = (Product) context.getBean("product3");
        Product product3b = (Product) context.getBean("product3");

        // Print information to console
        System.out.println("Product 1: " + product1);
        System.out.println("Product 2: " + product2);
        System.out.println("Product 3a: " + product3a);
        System.out.println("Product 3b: " + product3b);
    }
}
