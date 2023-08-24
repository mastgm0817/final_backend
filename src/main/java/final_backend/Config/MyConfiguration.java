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
<<<<<<< HEAD
//                        .allowedOrigins("http://localhost:3000")
//                        .allowedOrigins("https://luvoost.co.kr")
//                        .allowedOrigins("http://luvoost.co.kr")
//                        .allowedOrigins("https://180.150.207.73:32000")
//                        .allowedOrigins("http://180.150.207.73:32000")
=======
//                        .allowedOrigins(
//                                "http://localhost:3000",
//                                "https://luvoost.co.kr",
//                                "http://luvoost.co.kr",
//                                "https://180.150.207.73:31000",
//                                "http://180.150.207.73:31000"
//                        )
>>>>>>> 3c4a318f6b9bc04943c4391b1a0170db0c6bd30b
//                        .allowedMethods("GET", "POST", "PUT", "DELETE");
//            }
//        };
//    }
//}