// src/main/java/org/example/zippyziggy/Config/WebConfig.java
package org.example.zippyziggy.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**") // 모든 엔드포인트에 대해
                .allowedOrigins(
                        "http://localhost:5173", // React 개발 서버
                        "zippyziggy.site" // 배포된 프론트 도메인
                )
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}