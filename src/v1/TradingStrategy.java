public abstract class TradingStrategy {
    protected String strategyName;

    public TradingStrategy(String name) {
        this.strategyName = name;
    }
    public String getName() {
        return strategyName;
    }
    // Abstract method that MUST be overridden by child classes
    public abstract Trade executeTrade(HistoricalData data);
}