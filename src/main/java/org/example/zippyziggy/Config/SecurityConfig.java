package org.example.zippyziggy.Config;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletResponse;
import org.example.zippyziggy.Service.UserService;
import org.example.zippyziggy.Util.JwtAuthenticationFilter;
import org.example.zippyziggy.Util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;  // ObjectProvider 추가
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectProvider<UserService> userServiceProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(jwtTokenProvider, userServiceProvider);

        return http
                // (A) Security 필터 체인에서도 CORS 설정 활성화
                .cors(cors -> {
                    // 빈껍데기만 선언해도, WebConfig에 정의한 CORS 규칙이 자동으로 적용됩니다.
                })

                // (B) CSRF 비활성화 (API 서버라면 보통 disable)
                .csrf(csrf -> csrf.disable())

                // (C) 다음 경로들은 인증 없이 허용: signup, login, magazine, change-pw, predict
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/signup",
                                "/login",
                                "/magazine/**"
                        ).permitAll()
                        .anyRequest().authenticated())

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
