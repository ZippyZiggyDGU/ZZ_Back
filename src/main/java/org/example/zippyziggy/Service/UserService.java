package org.example.zippyziggy.Service;

import lombok.RequiredArgsConstructor;
import org.example.zippyziggy.Config.SecurityConfig;
import org.example.zippyziggy.DTO.request.ChangePWRequest;
import org.example.zippyziggy.DTO.request.LoginRequest;
import org.example.zippyziggy.DTO.request.SignupRequest;
import org.example.zippyziggy.DTO.response.TokenResponse;
import org.example.zippyziggy.Domain.User;
import org.example.zippyziggy.Repository.UserRepository;
import org.example.zippyziggy.Util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final Map<String, String> refreshTokenStorage = new HashMap<>();

    public UserDetails loadUserByUserId(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + userId));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserId())
                .password(user.getPassword())
                .authorities("ROLE_USER")
                .build();
    }

    public void signup(SignupRequest request) {
        if (userRepository.existsByUserId(request.getUserId())) {
            throw new RuntimeException("이미 존재하는 사용자입니다.");
        }
        User user = new User(request.getUserId(), passwordEncoder.encode(request.getPassword()), request.getGender(), request.getBirth());
        userRepository.save(user);
    }

    public TokenResponse login(LoginRequest request) {
        User user = userRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new RuntimeException("아이디 또는 비밀번호가 틀렸습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("아이디 또는 비밀번호가 틀렸습니다.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(request.getUserId());

        if (request.isRememberMe()) {
            String refreshToken = jwtTokenProvider.createRefreshToken(request.getUserId());
            refreshTokenStorage.put(request.getUserId(), refreshToken);
            return new TokenResponse(accessToken, refreshToken);
        } else {
            return new TokenResponse(accessToken, null);
        }
    }

    public TokenResponse reissue(String refreshToken) {
        String username = jwtTokenProvider.getUserIdFromToken(refreshToken);

        String savedRefreshToken = refreshTokenStorage.get(username);
        if (!refreshToken.equals(savedRefreshToken)) {
            throw new RuntimeException("리프레시 토큰이 유효하지 않습니다.");
        }

        String newAccessToken = jwtTokenProvider.createAccessToken(username);
        return new TokenResponse(newAccessToken, refreshToken);
    }

    public void changePW(ChangePWRequest request) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(request.getCurrentPW(), user.getPassword())) {
            throw new RuntimeException("현재 비밀번호가 일치하지 않습니다.");
        }

        user.setPassword(passwordEncoder.encode(request.getTargetPW()));
        userRepository.save(user);
    }

}
