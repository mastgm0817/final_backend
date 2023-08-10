package final_backend.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class MyConfiguration {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedOrigins("https://luvoost.co.kr")
                        .allowedOrigins("http://luvoost.co.kr")
                        .allowedOrigins("https://180.150.207.73:31000")
                        .allowedOrigins("http://180.150.207.73:31000")
                        .allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }
}