package final_backend;

import com.google.analytics.data.v1beta.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.google.analytics.data.v1beta.BetaAnalyticsDataClient;
import com.google.analytics.data.v1beta.DateRange;
import com.google.analytics.data.v1beta.Dimension;
import com.google.analytics.data.v1beta.Metric;
import com.google.analytics.data.v1beta.Row;
import com.google.analytics.data.v1beta.RunReportRequest;
import com.google.analytics.data.v1beta.RunReportResponse;
import com.google.analytics.data.v1beta.RunRealtimeReportRequest;
import com.google.analytics.data.v1beta.RunRealtimeReportResponse;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@SpringBootApplication
public class FinalBackendApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(FinalBackendApplication.class, args);
	}

}