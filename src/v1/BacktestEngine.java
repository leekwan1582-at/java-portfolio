import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BacktestEngine {
    // Directed Association: BacktestEngine "has a" TradingStrategy
    private TradingStrategy strategy;

    public BacktestEngine(TradingStrategy strategy) {
        this.strategy = strategy;
    }

public void runBacktest(String csvFilePath) {
    System.out.println("Starting backtest using strategy: " + strategy.getName());
    
    try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
        String line;
        boolean isHeader = true;
        
        while ((line = br.readLine()) != null) {
            if (isHeader) { 
                isHeader = false; 
                continue; // Skip the header row: Date,Open,High,Low,Close...
            }
            
            String[] values = line.split(",");
            String date = values[0];
            double price = Double.parseDouble(values[4]); // Index 4 is the Close price
            
            // Instantiate HistoricalData
            HistoricalData dailyData = new HistoricalData(date, price);
            
            // Execute strategy
            Trade trade = strategy.executeTrade(dailyData);
            if (trade != null) {
                System.out.print("[" + dailyData.getDate() + "] ");
                trade.printTradeDetails();
            }
        }
    } catch (IOException e) {
        System.out.println("Error reading CSV file: " + e.getMessage());
    }
}

    public static void main(String[] args) {
        // 1. Instantiate the strategy
        TradingStrategy myStrategy = new MovingAverageStrategy(151.00);
        
        // 2. Instantiate the engine and pass the strategy
        BacktestEngine engine = new BacktestEngine(myStrategy);
        
        // 3. Run the engine using the CSV file
        engine.runBacktest("../data/AAPL.csv");
    }
}