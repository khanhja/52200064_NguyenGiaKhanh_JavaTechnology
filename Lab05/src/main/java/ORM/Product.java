package ORM;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name="product")
@Entity
@Getter @Setter
public class Product {
    @Id
    private String productName;

    @Column(nullable = false)
    private int price;

    public Product() {}

    public Product(String productName, int price) {
        this.productName = productName;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("Product[name=%s, price=%d]", productName, price);
    }
}
