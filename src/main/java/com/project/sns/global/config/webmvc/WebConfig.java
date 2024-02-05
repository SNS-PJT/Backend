package com.project.sns.global.config.webmvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // 향후 도메인 주소 추가 예정
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("POST", "GET", "DELETE", "PATCH", "OPTIONS");
    }
}
