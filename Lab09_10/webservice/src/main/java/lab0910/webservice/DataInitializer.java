package lab0910.webservice;

import lab0910.webservice.Model.Product;
import lab0910.webservice.Model.Order;
import lab0910.webservice.Repository.ProductRepository;
import lab0910.webservice.Repository.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(ProductRepository productRepository, OrderRepository orderRepository) {
        return args -> {
            Order order1 = new Order();
            order1.setOrderNumber("ORD1001");
            order1.setTotalPrice(999.99);

            Order order2 = new Order();
            order2.setOrderNumber("ORD1002");
            order2.setTotalPrice(899.99);

            Order order3 = new Order();
            order3.setOrderNumber("ORD1003");
            order3.setTotalPrice(2399.99);

            orderRepository.saveAll(Arrays.asList(order1, order2, order3));

            productRepository.save(new Product("P001", "iPhone 15", 999.99, "iphone15.jpg", "Latest iPhone 15", order1));
            productRepository.save(new Product("P002", "Samsung Galaxy S24", 899.99, "galaxy_s24.jpg", "Samsung Galaxy S24", order2));
            productRepository.save(new Product("P003", "MacBook Pro 16", 2399.99, "macbook_pro_16.jpg", "MacBook Pro 16", order3));
        };
    }
}