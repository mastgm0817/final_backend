//package final_backend.Config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//
//@Configuration
//public class MyConfiguration {
//
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins(
//                                "http://localhost:3000",
//                                "https://luvoost.co.kr",
//                                "http://luvoost.co.kr",
//                                "https://180.150.207.73:31000",
//                                "http://180.150.207.73:31000"
//                        )
//                        .allowedMethods("GET", "POST", "PUT", "DELETE");
//            }
//        };
//    }
//}