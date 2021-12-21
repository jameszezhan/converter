package nyu.zc1069.converter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
// @EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/v1/**")
                .allowedOrigins(
                    "http://127.0.0.1:8081", 
                    "http://localhost:8081", 
                    "http://zeehon.us",
                    "https://zeehon.us",
                    "https://glacial-castle-98362.herokuapp.com");
    }
}
