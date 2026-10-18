
import java.util.LinkedList;
import java.util.Queue;

/**
 * Implements a Simple Moving Average (SMA) Breakout trading strategy.
 * <p>
 * This strategy maintains a rolling First-In, First-Out (FIFO) queue of recent
 * asset closing prices over a specified lookback period. It triggers a
 * <b>BUY</b> signal when the current price experiences a confirmed upward
 * breakout exceeding the moving average by a defined percentage buffer.
 * Conversely, it triggers a <b>SELL</b> signal if the price drops below the
 * baseline moving average.
 * </p>
 *
 * @author [Your Name]
 * @version 1.0
 */
public class MovingAverageStrategy extends TradingStrategy {

    /**
     * Percentage buffer above the moving average required to confirm a valid
     * breakout (1%).
     */
    private static final double BREAKOUT_BUFFER_MULTIPLIER = 1.01;

    private final int period;
    private final Queue<Double> priceWindow;

    /**
     * Constructs a new MovingAverageStrategy with a specified lookback window
     * period.
     *
     * @param period The number of sessions required to calculate the moving
     * average baseline
     */
    public MovingAverageStrategy(int period) {
        super("Moving Average (MA) Breakout (" + period + "-Day)");
        this.period = period;
        this.priceWindow = new LinkedList<>();
    }

    /**
     * Evaluates a daily market data point, manages the rolling price queue,
     * calculates the moving average, and checks for breakout or breakdown
     * signals.
     *
     * @param data The current {@link HistoricalData} point containing the
     * session close price
     * @return A {@link Trade} execution signal if breakout criteria are met, or
     * {@code null} otherwise
     */
    @Override
    public Trade executeTrade(HistoricalData data) {
        double currentPrice = data.getClosePrice();

        // Append current session price to the rolling sliding-window queue
        priceWindow.add(currentPrice);

        // Enforce technical indicator lookback warm-up period
        if (priceWindow.size() < period) {
            return null;
        }

        // Maintain fixed window size by evicting the oldest historical price entry (FIFO)
        if (priceWindow.size() > period) {
            priceWindow.poll();
        }

        // Compute the Simple Moving Average (SMA) baseline
        double sum = 0.0;
        for (double price : priceWindow) {
            sum += price;
        }
        double movingAverage = sum / period;

        // Evaluate entry and exit rule criteria
        if (currentPrice > (movingAverage * BREAKOUT_BUFFER_MULTIPLIER)) {
            return new Trade("AAPL", currentPrice, true);  // Bullish breakout entry signal
        } else if (currentPrice < movingAverage) {
            return new Trade("AAPL", currentPrice, false); // Bearish breakdown exit signal
        }

        return null;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Clears all historical pricing records from the rolling queue, restoring
     * the strategy to its pristine initial state.
     * </p>
     */
    @Override
    public void reset() {
        priceWindow.clear();
    }
}
