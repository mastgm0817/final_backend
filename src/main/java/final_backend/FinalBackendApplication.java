package final_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinalBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinalBackendApplication.class, args);
	}
//	@Bean
//	public ServletListenerRegistrationBean<VisitorCounterListener> listenerRegistrationBean() {
//		ServletListenerRegistrationBean<VisitorCounterListener> listenerBean =
//				new ServletListenerRegistrationBean<>();
//		listenerBean.setListener(new VisitorCounterListener());
//		return listenerBean;
}
