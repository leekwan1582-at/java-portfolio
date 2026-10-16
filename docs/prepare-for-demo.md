### Run Single backtesting
BacktestEngine.runSingleTest(new MACDStrategy(), "data/AAPL.csv", "2020-01-01", "2020-12-31");

### cli command

javac src/v2/*.java

java -cp src/v2 BacktestEngine


To ace your oral presentation and clarification session, you need to walk your instructor through your PDF document and BlueJ project by explicitly connecting every talking point back to the module’s **Learning Outcomes (LO1, LO4, LO5)** and marking criteria.

Here is a structured, step-by-step presentation script and guide:

---

### Step 1: Introduction & Problem Statement (Covering LO1)
* **What to show:** Open your PDF to the Write-Up section and briefly show your BlueJ project structure.
* **What to say:** 
  > *"For my portfolio project, I chose a Trading Backtesting Engine that processes historical financial data from CSV files. I chose Java for this problem because of two core strengths: **strong compile-time type safety**, which prevents financial calculation errors, and **high-performance execution capability**, which is essential when iterating through large datasets row-by-row. 
  > 
  > As an alternative, Python is frequently used in quantitative finance, but it is an interpreted, dynamically typed language that can suffer from performance bottlenecks (due to the GIL) when scaling heavy mathematical computations or raw file I/O operations without native C-extensions. Java provides a more robust, performant environment for a compiled application architecture."*
* **Target Hit:** **LO1** (Determine when use cases require Java) & Write-up criteria.

---

### Step 2: Presenting the Class Diagram (Covering LO4 & LO5)
* **What to show:** Open your PDF to the **Class Diagram** (and optionally have BlueJ open side-by-side to show the live project).
* **What to say:** Walk through the diagram systematically, pointing out how it meets every single structural requirement of the rubric:
  1. **At least three classes:** *"My design features multiple classes, including `BacktestEngine`, `HistoricalData`, `Trade`, `TradingStrategy`, and polymorphic implementations like `MovingAverageStrategy` and `RSIStrategy`."*
  2. **At least one instantiable class:** *"Classes like `Trade` and `HistoricalData` are instantiated constantly during runtime to package individual rows of data and buy/sell signals."*
  3. **Abstract Base Class & Inheritance:** *"I designed an abstract base class called `TradingStrategy`. From this, `MovingAverageStrategy` inherits using single-level inheritance (`extends TradingStrategy`)."*
  4. **Overridden Method:** *"The abstract class defines an abstract method `executeTrade(HistoricalData data)`. My `MovingAverageStrategy` overrides this method using the `@Override` annotation to provide its specific breakout logic."*
  5. **Directed Association:** *"The `BacktestEngine` has a directed association pointing to `TradingStrategy` (and `HistoricalData`), meaning the engine utilizes these classes to perform its tasks."*
  6. **Attributes:** *"Relevant business attributes are clearly defined, such as `closePrice` and `date` in `HistoricalData`, and `executionPrice` and `quantity` in `Trade`."*
* **Target Hit:** **LO4** & **LO5**, plus all Class Diagram marking criteria.

---

### Step 3: Presenting the Object Diagram
* **What to show:** Open your PDF to the **Object Diagram**. 
* **What to say:** 
  > *"While a class diagram shows the blueprint, the object diagram shows a snapshot of the system's state at runtime. For example, here you can see an instance of `engine: BacktestEngine` configured with a date range, pointing to a specific instance of `maStrategy: MovingAverageStrategy` with a period of 20, processing an instance of `dailyData: HistoricalData` where `closePrice = 152.50`, which successfully yields an instance of `trade1: Trade` where `isBuy = true`."*
* **Target Hit:** Object Diagram marking criteria (showing values for at least one object per class).

---

### Step 4: Live Demo in BlueJ (Proving it works)
* **What to do:** Switch to your **BlueJ** window.
* **What to do/say:** 
  1. Show how BlueJ visually maps your classes. 
  2. Right-click `BacktestEngine`, select `void main(String[] args)`, and run it.
  3. Point to the terminal output showing real CSV parsing and trades triggering.
  4. Explain: *"Because of polymorphism, if I want to swap my Moving Average strategy for an RSI strategy, I don't need to rewrite the `BacktestEngine`. I simply inject the new strategy object via encapsulation, proving the modularity of my software design."*

---

### Pro-Tips for the Q&A Session:
If the instructor asks probing questions, keep these answers in mind:
* **Q: *"Why did you use an abstract class instead of an interface?"***
  * **A:** *"I used an abstract class because I wanted to share common behavior and attributes (like the `strategyName` and the `getName()` method) across all child strategies, while forcing them to implement their own unique mathematical evaluation via the abstract method."*
* **Q: *"How does encapsulation apply to your code?"***
  * **A:** *"Fields like `dataCsv`, `startDate`, and `endDate` in `BacktestEngine` are marked `private` to protect the internal state, and are safely accessed or modified using controlled getter and setter methods."*