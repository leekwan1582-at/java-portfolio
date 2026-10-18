
/**
 * Serves as the abstract base class for all algorithmic trading strategies.
 * <p>
 * This class defines the common contract and lifecycle hooks required by the
 * {@link BacktestEngine} to execute sequential data evaluation and manage state
 * polymorphically. Concrete subclasses must implement the
 * {@link #executeTrade(HistoricalData)} method to define custom indicator
 * logic.
 * </p>
 *
 * @author [ai.studio]
 * @version 1.0
 */
public abstract class TradingStrategy {

    /**
     * The descriptive name of the strategy.
     */
    protected String strategyName;

    /**
     * Initializes the strategy with a designated name identifier.
     *
     * @param name The name of the strategy
     */
    public TradingStrategy(String name) {
        this.strategyName = name;
    }

    /**
     * Retrieves the identifier name of the strategy.
     *
     * @return The strategy name string
     */
    public String getName() {
        return strategyName;
    }

    /**
     * Evaluates a single historical data point sequentially and determines
     * whether a trade signal (Buy/Sell) should be triggered.
     *
     * @param data The current {@link HistoricalData} point containing market
     * pricing details
     * @return A {@link Trade} object if a breakout or indicator threshold is
     * met, or {@code null} otherwise
     */
    public abstract Trade executeTrade(HistoricalData data);

    /**
     * Resets the internal state and rolling indicators of the strategy.
     * <p>
     * This lifecycle method ensures that internal collections, accumulators, or
     * rolling queues are wiped clean, allowing strategy instances to be safely
     * reused across multiple backtest runs.
     * </p>
     */
    public void reset() {
        // Default implementation is a no-op; subclasses with state override this method.
    }
}
