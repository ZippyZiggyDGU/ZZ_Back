package org.example.zippyziggy.Controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.zippyziggy.DTO.request.LoginRequest;
import org.example.zippyziggy.Domain.User;
import org.example.zippyziggy.Service.UserService;
import org.example.zippyziggy.Util.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest request, HttpServletResponse response) {

        User user = userService.authenticateUser(request.getUserId(), request.getPassword());

        long tokenExpiration = request.isRememberMe() ? jwtUtil.getLongExpiration() : jwtUtil.getShortExpiration();
        String token = jwtUtil.generateToken(user.getUserId(), tokenExpiration);

        Cookie jwtCookie = new Cookie("AUTH_TOKEN", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge((int) (tokenExpiration / 1000));
        response.addCookie(jwtCookie);

        return ResponseEntity.ok().build();
    }
}
