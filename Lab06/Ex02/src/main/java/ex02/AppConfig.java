package ex02;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

    @Bean
    @Scope("prototype")
    public Product product1() {
        Product product = new Product();
        product.setName("Macbook Pro M1");
        product.setPrice(1000);
        return product;
    }

    @Bean
    @Scope("prototype")
    public Product product2() {
        return new Product(1002, "iPhone 15 Pro Max", 500, "Apple Flagship");
    }

    // Bean singleton
    @Bean
    public Product product3() {
        return new Product(1003, "iPad Pro M1", 300, "Apple Flagship");
    }
}
