import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.ZoneId;

public class YahooFinanceDownloader {

    public static void downloadData(String ticker, String startDate, String endDate, String savePath) {
        try {
            long period1 = LocalDate.parse(startDate).atStartOfDay(ZoneId.of("UTC")).toEpochSecond();
            long period2 = LocalDate.parse(endDate).atStartOfDay(ZoneId.of("UTC")).toEpochSecond();

            String urlString = String.format(
                "https://query1.finance.yahoo.com/v7/finance/download/%s?period1=%d&period2=%d&interval=1d&events=history&includeAdjustedClose=true",
                ticker, period1, period2
            );

            System.out.println("Downloading data for " + ticker + "...");
            
            // Modern Java approach: Use URI to create the URL, avoiding the deprecated constructor warning
            URL url = new URI(urlString).toURL();
            
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            if (connection.getResponseCode() == 200) {
                try (InputStream in = connection.getInputStream()) {
                    Files.copy(in, Paths.get(savePath), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Successfully saved data to: " + savePath);
                }
            } else {
                System.out.println("Failed to download data. HTTP Response Code: " + connection.getResponseCode());
            }

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String ticker = "AAPL";
        String startDate = "2020-01-01";
        String endDate = "2025-12-31";
        String savePath = "../data/" + ticker + "_historical.csv";

        downloadData(ticker, startDate, endDate, savePath);
    }
}