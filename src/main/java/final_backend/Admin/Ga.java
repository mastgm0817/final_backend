package final_backend.Admin;

import com.google.analytics.data.v1beta.*;

public class Ga {
    public static RunReportResponse sampleRunReport(String propertyId, String Dim, String Met, String startDate, String endDate) throws Exception {
        try (BetaAnalyticsDataClient analyticsData = BetaAnalyticsDataClient.create()) {
            RunReportRequest request =
                    RunReportRequest.newBuilder()
                            .setProperty("properties/" + propertyId)
                            //						.addDimensions(Dimension.newBuilder().setName("pageTitle"))
                            .addDimensions(Dimension.newBuilder().setName(Dim))
                            .addMetrics(Metric.newBuilder().setName(Met))
                            //.addMetrics(Metric.newBuilder().setName("itemsViewed"))
                            //.addMetrics(Metric.newBuilder().setName("activeUsers"))
                            .addDateRanges(DateRange.newBuilder().setStartDate(startDate).setEndDate(endDate))
                            .build();

            RunReportResponse response = analyticsData.runReport(request);

            System.out.println("Report result:" + response.toString());
            System.out.println("Report row:" + response.getRowsList());
            for (Row row : response.getRowsList()) {
                String eventName = row.getDimensionValues(0).getValue();
                long eventCount = Long.parseLong(row.getMetricValues(0).getValue());
//				long eventCount = Long.parseLong(row.getMetricValues(0).getValue());

                System.out.println("eventName: " + eventName);
//				System.out.println("Event Count: " + eventCount);
                System.out.println("eventCount: " + eventCount);
            }
            return response;
        }
    }
}
