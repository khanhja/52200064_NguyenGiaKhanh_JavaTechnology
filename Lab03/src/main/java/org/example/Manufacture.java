package org.example;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name="Manufacture")
@Getter @Setter
public class Manufacture {
    @Id
    private String id;

    @Size(min = 3, max = 128)
    private String name;

    private String location;

    private int employee;

    @OneToMany(mappedBy="manufacture", cascade=CascadeType.ALL)
    private List<Phone> phones;

    @Override
    public String toString() {
        return "Manufacture{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", employee=" + employee +
                ", phones=" + phones +
                '}';
    }
}
