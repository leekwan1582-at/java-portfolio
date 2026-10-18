
import java.util.LinkedList;
import java.util.Queue;

/**
 * Implements the Relative Strength Index (RSI) momentum oscillator trading
 * strategy.
 * <p>
 * The RSI measures the speed and change of price movements on a scale of 0 to
 * 100. This strategy triggers a <b>BUY</b> signal when the asset is deemed
 * oversold (RSI &lt; 30) and a <b>SELL</b> signal when the asset is deemed
 * overbought (RSI &gt; 70). It maintains state via a rolling queue of
 * historical price changes and supports state resets.
 * </p>
 *
 * @author [Your Name]
 * @version 1.0
 */
public class RSIStrategy extends TradingStrategy {

    private static final double OVERSOLD_THRESHOLD = 30.0;
    private static final double OVERBOUGHT_THRESHOLD = 70.0;

    private final int period;
    private final Queue<Double> priceWindow;
    private double previousClose;

    /**
     * Constructs a new RSIStrategy with a specified lookback period.
     *
     * @param period The rolling window size for calculating average gains and
     * losses (commonly 14)
     */
    public RSIStrategy(int period) {
        super("Relative Strength Index (RSI) Strategy (" + period + "-Period)");
        this.period = period;
        this.priceWindow = new LinkedList<>();
        this.previousClose = -1.0;
    }

    /**
     * Evaluates a daily market data point, updates the rolling price change
     * window, computes the RSI indicator, and checks for overbought/oversold
     * breakout conditions.
     *
     * @param data The current {@link HistoricalData} point containing the
     * closing price
     * @return A {@link Trade} execution signal if thresholds are crossed, or
     * {@code null} otherwise
     */
    @Override
    public Trade executeTrade(HistoricalData data) {
        double currentClose = data.getClosePrice();

        // Seed the initial price point; absolute first tick cannot calculate a price delta
        if (previousClose == -1.0) {
            previousClose = currentClose;
            return null;
        }

        double change = currentClose - previousClose;
        previousClose = currentClose;

        priceWindow.add(change);
        if (priceWindow.size() > period) {
            priceWindow.poll();
        }

        // Enforce the technical indicator warm-up period
        if (priceWindow.size() < period) {
            return null;
        }

        // Aggregate rolling gains and absolute losses over the lookback window
        double totalGain = 0.0;
        double totalLoss = 0.0;
        for (double diff : priceWindow) {
            if (diff > 0) {
                totalGain += diff;
            } else {
                totalLoss += Math.abs(diff);
            }
        }

        double avgGain = totalGain / period;
        double avgLoss = totalLoss / period;

        // Prevent mathematical division-by-zero exceptions during zero-loss periods
        if (avgLoss == 0.0) {
            return null;
        }

        double relativeStrength = avgGain / avgLoss;
        double rsi = 100.0 - (100.0 / (1.0 + relativeStrength));

        // Evaluate core RSI momentum boundaries
        if (rsi < OVERSOLD_THRESHOLD) {
            return new Trade("AAPL", currentClose, true);  // Bullish entry signal
        } else if (rsi > OVERBOUGHT_THRESHOLD) {
            return new Trade("AAPL", currentClose, false); // Bearish exit signal
        }

        return null;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Clears the rolling price-change queue and resets the baseline tracking
     * price to prepare the indicator for a fresh backtest cycle.
     * </p>
     */
    @Override
    public void reset() {
        priceWindow.clear();
        previousClose = -1.0;
    }
}
