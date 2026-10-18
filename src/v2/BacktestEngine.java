
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The BacktestEngine coordinates the execution of trading strategies against
 * historical market data loaded from CSV files.
 */
public class BacktestEngine {

    private TradingStrategy strategy;
    private String dataCsv = "data/AAPL.csv";
    private String startDate;
    private String endDate;

    /**
     * Constructs a BacktestEngine with a specified strategy and dataset
     * configuration.
     *
     * @param strategy The trading algorithm to test
     * @param dataCsv Path to the historical CSV file
     * @param startDate The start date for the backtest window (YYYY-MM-DD)
     * @param endDate The end date for the backtest window (YYYY-MM-DD)
     */
    public BacktestEngine(TradingStrategy strategy, String dataCsv, String startDate, String endDate) {
        this.strategy = strategy;
        this.dataCsv = dataCsv;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Constructs a BacktestEngine using the default CSV file path.
     */
    public BacktestEngine(TradingStrategy strategy, String startDate, String endDate) {
        this(strategy, "data/AAPL.csv", startDate, endDate);
    }

    public TradingStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(TradingStrategy strategy) {
        this.strategy = strategy;
    }

    public String getDataCsv() {
        return dataCsv;
    }

    public void setDataCsv(String dataCsv) {
        this.dataCsv = dataCsv;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "BacktestEngine Configuration {"
                + "\n  Strategy = " + (strategy != null ? strategy.getName() : "None")
                + "\n  Data File = " + dataCsv
                + "\n  Start Date = " + startDate
                + "\n  End Date = " + endDate
                + "\n}";
    }

    /**
     * Executes the backtest loop, parsing historical prices sequentially and
     * delegating trade signals to the assigned strategy.
     */
    public void runBacktest() {
        System.out.println("---------------------------------------------------");
        System.out.println(this.toString());
        System.out.println("---------------------------------------------------");

        if (strategy == null) {
            System.out.println("Error: No strategy assigned to the engine!");
            return;
        }

        strategy.reset();
        int tradesExecuted = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(dataCsv))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] values = line.split(",");
                String date = values[0];
                // Column index 4 corresponds to the adjusted/close price in standard Yahoo Finance CSV format
                double price = Double.parseDouble(values[4]);

                // Filter data strictly within the targeted backtest time window
                if (date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0) {
                    HistoricalData dailyData = new HistoricalData(date, price);
                    Trade trade = strategy.executeTrade(dailyData);

                    if (trade != null) {
                        System.out.print("[" + dailyData.getDate() + "] ");
                        trade.printTradeDetails();
                        tradesExecuted++;
                    }
                }
            }
            System.out.println("Backtest completed. Total trades executed: " + tradesExecuted);

        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
    }

    /**
     * Utility method to quickly spin up and execute a single backtest run.
     */
    public static void runSingleTest(TradingStrategy strategy, String csvPath, String start, String end) {
        BacktestEngine tempEngine = new BacktestEngine(strategy, csvPath, start, end);
        tempEngine.runBacktest();
    }

    public static void main(String[] args) {
        TradingStrategy maStrategy = new MovingAverageStrategy(20);
        BacktestEngine engine = new BacktestEngine(maStrategy, "data/AAPL.csv", "2020-01-01", "2020-12-31");
        engine.runBacktest();

        System.out.println("\n===================================================\n");

        engine.setStrategy(new RSIStrategy(14));
        engine.setStartDate("2020-01-01");
        engine.setEndDate("2020-12-31");
        engine.runBacktest();
    }
}
