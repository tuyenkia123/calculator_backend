package vn.salary.calculator.authentication.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import vn.salary.calculator.shared.snowflake.Snowflake;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String password;

    private String email;

    private String role = "USER";

    public static User create(String username, String password, String fullName) {
        User user = new User();
        user.setId(Snowflake.poll());
        user.setUsername(username);
        user.setPassword(password);
        user.setFullName(fullName);
        return user;
    }
}
