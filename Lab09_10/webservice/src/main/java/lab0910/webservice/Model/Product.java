package lab0910.webservice.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    private double price;

    private String illustration;

    private String description;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public Product(String code, String name, double price, String illustration, String description, Order order) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.illustration = illustration;
        this.description = description;
        this.order = order;
    }

    public Product() {

    }
}