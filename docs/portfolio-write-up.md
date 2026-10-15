## Portfolio project - **Trading Backtesting Engine** that reads historical data from CSV filesthis.


### 1. Write-Up Requirements
*   **Problem Description:** User need to simulate trading strategies against historical market data (from CSV files) to evaluate their profitability before risking real money.
*   **Justification for Java (Strength):** Java is highly suitable for this because of its **strong typing and Object-Oriented paradigm**, which are critical for financial software to prevent errors and model complex real-world entities (e.g., Trades, Portfolios, Assets). Additionally, Java's **multithreading capabilities** (Concurrency API) allow it to process large CSV datasets or run multiple backtests simultaneously much faster than single-threaded languages.
*   **The Alternative (Why it's not suitable):** A common alternative is **Python**. While Python is popular for data science, it is an interpreted, dynamically-typed language and is restricted by the Global Interpreter Lock (GIL). For a pure backtesting engine that needs to parse massive CSV files and iterate through millions of rows tick-by-tick, Python can be significantly slower than Java's compiled, multi-threaded execution unless you rely on external C-libraries (like Pandas). Java offers better native performance and compile-time safety for building the core engine.

### 2. Class Diagram Requirements
Your idea naturally lends itself to a rich class structure that easily ticks off all the rubric requirements. Here is how you can design it to ensure you get full marks:

*   **At least three classes:** `Trade`, `HistoricalData`, `MovingAverageStrategy`. (You will have plenty).
*   **At least one interface or abstract base class:** 
    *   Create an abstract class called `TradingStrategy`.
*   **Demonstrate inheritance at least once (Single-level for LO4):** 
    *   Create a class `MovingAverageStrategy` that `extends TradingStrategy`.
*   **Override at least one method:** 
    *   In the `TradingStrategy` abstract class, define an abstract method: `public abstract void executeTrade(HistoricalData data);`
    *   In `MovingAverageStrategy`, use the `@Override` annotation and implement the `executeTrade()` method based on moving average logic.
*   **At least one directed association:** 
    *   The `BacktestEngine` class *uses* `HistoricalData`. A directed arrow from `BacktestEngine` to `HistoricalData` shows that the engine knows about the data, but the data doesn't know about the engine.
*   **At least one class that can be instantiated:** 
    *   `Trade`, `HistoricalData`, and `MovingAverageStrategy` can all be instantiated using the `new` keyword.
*   **Two attributes relevant to the business case:**
    *   `Trade` class: `double executionPrice`, `int quantity`.
    *   `HistoricalData` class: `String date`, `double closePrice`.

### 3. Object Diagram Requirements
**Requirement:** Show one possible state of all the classes with values.
An object diagram is simply a snapshot of your system at runtime. For a backtesting app, this is very easy to visualize. Your diagram will show:
*   **`myEngine: BacktestEngine`** (State: `isRunning = true`, `currentBalance = 10500.00`)
*   **`appleData: HistoricalData`** (State: `ticker = "AAPL"`, `date = "2023-10-15"`, `closePrice = 150.25`)
*   **`maStrategy: MovingAverageStrategy`** (State: `shortWindow = 10`, `longWindow = 50`)
*   **`trade1: Trade`** (State: `ticker = "AAPL"`, `executionPrice = 150.25`, `quantity = 10`, `isBuy = true`)

### 4. Preparation for Oral Clarification (LOs)
By using this design, you are perfectly set up for your presentation:
*   **LO1 (Determine use cases for Java):** I can confidently speak about execution speed, type safety in financial logic, and platform independence (running the backtester on Windows, Mac, or Linux without changing code).
*   **LO4 (Design simple classes...):** I will be able to point directly to your abstract `TradingStrategy` class, show how `MovingAverageStrategy` inherits from it, and explain how polymorphism allows your `BacktestEngine` to run *any* strategy seamlessly.
*   **LO5 (UML Class Diagrams):** I will be able to explain the difference between your inheritance arrows (hollow triangle) and directed association arrows (open arrowhead).

### Summary Checklist for your Submission
1. [ ] Write-up explaining the Backtester, praising Java's performance/safety, and critiquing Python's speed/GIL.
2. [ ] UML Class Diagram with an abstract `Strategy` class, a concrete `Strategy` class, a `Trade` class, and a `BacktestEngine` class.
3. [ ] UML Object Diagram showing a snapshot of a successful Apple (AAPL) trade being executed.
4. [ ] Export it all as **ONE single PDF document**.