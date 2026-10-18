
/**
 * Represents a single daily historical market data point for an asset.
 * <p>
 * This class acts as a lightweight domain data carrier (DTO / Value Object),
 * encapsulating essential trading metrics such as the calendar date and the
 * asset's closing price, which are passed sequentially into active trading
 * strategies.
 * </p>
 *
 * @author [ai.studio]
 * @version 1.0
 */
public class HistoricalData {

    private final String date;
    private final double closePrice;

    /**
     * Constructs a new HistoricalData instance with the specified date and
     * closing price.
     *
     * @param date The calendar date of the market session (formatted as
     * YYYY-MM-DD)
     * @param closePrice The official closing price of the asset for that
     * session
     */
    public HistoricalData(String date, double closePrice) {
        this.date = date;
        this.closePrice = closePrice;
    }

    /**
     * Retrieves the calendar date of the market session.
     *
     * @return The date string
     */
    public String getDate() {
        return date;
    }

    /**
     * Retrieves the closing price of the asset.
     *
     * @return The close price as a double precision value
     */
    public double getClosePrice() {
        return closePrice;
    }
}
