package org.example;

import jakarta.persistence.*;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="Phone")
@Getter @Setter

public class Phone {
    @Id
    private String id;

    @Column(nullable = false)
    @Size(min = 3, max = 128)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String color;

    private String country;

    private int quantity;

    @ManyToOne
    @JoinColumn(name="manufacture_id")
    private Manufacture manufacture;

    @Override
    public String toString() {
        return "Phone{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", color='" + color + '\'' +
                ", country='" + country + '\'' +
                ", quantity=" + quantity +
                ", manufacture=" + manufacture.getId() +
                '}';
    }
}
