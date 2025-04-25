package org.example.zippyziggy.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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

    @Column(nullable = false)
    private int gender;

    @Column(nullable = false, length = 8)
    private String birth;

    protected User() {}

    public User(String userId, String password, int gender, String birth) {
        this.userId = userId;
        this.password = password;
        this.gender = gender;
        this.birth = birth;
    }
}
