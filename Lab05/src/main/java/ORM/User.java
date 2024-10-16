package ORM;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name="User")
@Entity
@Getter @Setter
public class User {
    @Id
    private String email;

    @Column(nullable=false)
    private String fullName;

    @Column(nullable=false)
    private String password;

    public User(String email, String fullName, String password) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
    }

    public User() {}

    @Override
    public String toString() {
        return String.format("User[email=%s, fullName=%s, password=%s]", email, fullName, password);
    }
}
