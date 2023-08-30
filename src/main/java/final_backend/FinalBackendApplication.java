package final_backend;

import com.google.analytics.data.v1beta.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class FinalBackendApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(FinalBackendApplication.class, args);
	}

}