
import java.util.ArrayList;
import java.util.List;

/**
 * Implements the Moving Average Convergence Divergence (MACD) crossover trading
 * strategy.
 * <p>
 * The MACD indicator is a trend-following momentum indicator that shows the
 * relationship between two exponential moving averages (EMAs) of a security’s
 * price. This strategy tracks a Fast EMA (12-period), a Slow EMA (26-period),
 * and a Signal Line (9-period). It triggers a <b>BUY</b> signal on a bullish
 * crossover (MACD line crosses above the Signal line) and a <b>SELL</b> signal
 * on a bearish crossover (MACD line crosses below the Signal line).
 * </p>
 *
 * @author [ai.studio]
 * @version 1.0
 */
public class MACDStrategy extends TradingStrategy {

    private static final int FAST_SPAN = 12;
    private static final int SLOW_SPAN = 26;
    private static final int SIGNAL_SPAN = 9;

    // Smoothing multiplier formula: k = 2 / (span + 1)
    private final double kFast = 2.0 / (FAST_SPAN + 1);
    private final double kSlow = 2.0 / (SLOW_SPAN + 1);
    private final double kSignal = 2.0 / (SIGNAL_SPAN + 1);

    private final List<Double> priceHistory;
    private double fastEMA = 0.0;
    private double slowEMA = 0.0;
    private double signalEMA = 0.0;
    private boolean isInitialized = false;

    /**
     * Constructs a new MACDStrategy utilizing standard financial market
     * settings (Fast: 12, Slow: 26, Signal: 9).
     */
    public MACDStrategy() {
        super("MACD Crossover Strategy (" + FAST_SPAN + ", " + SLOW_SPAN + ", " + SIGNAL_SPAN + ")");
        this.priceHistory = new ArrayList<>();
    }

    /**
     * Evaluates a daily market data point, maintains rolling price history,
     * updates exponential moving averages, and checks for MACD signal line
     * crossovers.
     *
     * @param data The current {@link HistoricalData} point containing market
     * pricing details
     * @return A {@link Trade} execution signal upon crossover detection, or
     * {@code null} otherwise
     */
    @Override
    public Trade executeTrade(HistoricalData data) {
        double price = data.getClosePrice();
        priceHistory.add(price);

        // Enforce the slow span window requirement to accumulate adequate data points
        if (priceHistory.size() < SLOW_SPAN) {
            return null;
        }

        // Initialize EMA baselines using a Simple Moving Average (SMA) for the initial block
        if (!isInitialized) {
            fastEMA = calculateInitialSMA(priceHistory, FAST_SPAN);
            slowEMA = calculateInitialSMA(priceHistory, SLOW_SPAN);
            signalEMA = fastEMA - slowEMA;
            isInitialized = true;
            return null;
        }

        // Dynamically compute recurring Exponential Moving Averages (EMA)
        fastEMA = (price * kFast) + (fastEMA * (1.0 - kFast));
        slowEMA = (price * kSlow) + (slowEMA * (1.0 - slowEMA));

        double macdLine = fastEMA - slowEMA;
        double previousSignal = signalEMA;
        signalEMA = (macdLine * kSignal) + (signalEMA * (1.0 - kSignal));

        // Evaluate MACD Crossover conditions
        if (previousSignal <= signalEMA && macdLine > signalEMA) {
            return new Trade("AAPL", price, true);  // Bullish crossover (Buy signal)
        } else if (previousSignal >= signalEMA && macdLine < signalEMA) {
            return new Trade("AAPL", price, false); // Bearish crossover (Sell signal)
        }

        return null;
    }

    /**
     * Calculates the initial Simple Moving Average (SMA) required to seed the
     * exponential smoothing accumulators.
     *
     * @param list The full collection of accumulated historical prices
     * @param period The lookback period window size
     * @return The calculated simple average value
     */
    private double calculateInitialSMA(List<Double> list, int period) {
        double sum = 0.0;
        int start = list.size() - period;
        for (int i = start; i < list.size(); i++) {
            sum += list.get(i);
        }
        return sum / period;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Clears internal price buffers, resets tracking indicators to zero, and
     * flags the calculation engine as uninitialized for a fresh test cycle.
     * </p>
     */
    @Override
    public void reset() {
        priceHistory.clear();
        fastEMA = 0.0;
        slowEMA = 0.0;
        signalEMA = 0.0;
        isInitialized = false;
    }
}
