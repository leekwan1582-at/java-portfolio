public class HistoricalData {
    // Two attributes relevant to the business case
    private String date;
    private double closePrice;

    public HistoricalData(String date, double closePrice) {
        this.date = date;
        this.closePrice = closePrice;
    }

    public String getDate() { return date; }
    public double getClosePrice() { return closePrice; }
}