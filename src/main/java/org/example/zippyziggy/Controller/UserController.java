package org.example.zippyziggy.Controller;

import lombok.RequiredArgsConstructor;
import org.example.zippyziggy.DTO.request.LoginRequest;
import org.example.zippyziggy.DTO.request.SignupRequest;
import org.example.zippyziggy.DTO.response.TokenResponse;
import org.example.zippyziggy.Service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest request) {
        userService.signup(request);
        return "회원가입 성공!";
    }

    @PostMapping("/reissue")
    public TokenResponse reissue(@RequestHeader("Authorization") String bearerToken) {
        String refreshToken = bearerToken.substring(7);
        return userService.reissue(refreshToken);
    }

}
