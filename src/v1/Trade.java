public class Trade {
    private String ticker;
    private double executionPrice;
    private boolean isBuy;

    public Trade(String ticker, double executionPrice, boolean isBuy) {
        this.ticker = ticker;
        this.executionPrice = executionPrice;
        this.isBuy = isBuy;
    }

    public void printTradeDetails() {
        String action = isBuy ? "BOUGHT" : "SOLD";
        System.out.println("Trade Executed: " + action + " " + ticker + " @ $" + executionPrice);
    }
}