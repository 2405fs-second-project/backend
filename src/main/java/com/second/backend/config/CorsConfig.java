package com.second.backend.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//CORS 설정 (addCorsMappings):
//클라이언트 측의 브라우저가 다른 출처의 리소스에 접근할 수 있도록 허용합니다.
//CORS 정책을 설정하여 외부 도메인에서 오는 요청을 제어합니다.

@Configuration
public class CorsConfig implements WebMvcConfigurer{
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 특정 출처(도메인)에서 오는 요청을 허용
                .allowedOrigins("http://localhost:3000") // 허용할 오리진(프론트엔드 애플리케이션의 주소)
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드
                .allowedHeaders("*"); // 허용할 HTTP 헤더
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("img/**")
                .addResourceLocations("file://user/local/images/");
    }


}
