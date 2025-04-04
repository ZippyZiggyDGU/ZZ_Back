package org.example.zippyziggy.Service;

import lombok.RequiredArgsConstructor;
import org.example.zippyziggy.Config.SecurityConfig;
import org.example.zippyziggy.Domain.User;
import org.example.zippyziggy.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User authenticateUser(String userId, String rawPassword) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        return user;
    }

}
