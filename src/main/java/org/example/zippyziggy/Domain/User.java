package org.example.zippyziggy.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @Column(nullable = false, length = 20, unique = true)
    private String userId;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false)
    private boolean rememberMe;

    protected User() {}

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

}
