public class MovingAverageStrategy extends TradingStrategy {
    private double thresholdPrice;

    public MovingAverageStrategy(double thresholdPrice) {
        super("Moving Average Breakout");
        this.thresholdPrice = thresholdPrice;
    }

    // Overriding the abstract method from the base class
    @Override
    public Trade executeTrade(HistoricalData data) {
        // Simple logic: Buy if the close price is higher than our threshold
        if (data.getClosePrice() > thresholdPrice) {
            return new Trade("AAPL", data.getClosePrice(), true);
        }
        return null; // No trade executed
    }
}