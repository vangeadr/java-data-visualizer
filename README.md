# 📊 Automated Linear Regression Engine & Data Visualizer

A high-performance, modular Java application built from scratch to ingest large-scale CSV datasets, execute real-time statistical analysis, and dynamically render coordinate plots with exact-fit regression lines onto a custom pixel canvas.

---

## 🛠️ System Architecture & Engineering

This project was engineered to showcase robust Object-Oriented Programming (OOP) principles, clean algorithmic design, and data pipeline processing without relying on bloated external libraries.

### 🏛️ Component Breakdown

| Module | Core Responsibility | Technical Highlights |
| :--- | :--- | :--- |
| **`Main.java`** | Core Orchestrator | Manages the application lifecycle, triggers the ETL pipeline, and initializes visual outputs. |
| **`FileParser.java`** | Ingestion Engine | Streams raw CSV data, filters missing values, handles data formatting safely, and builds memory-mapped objects. |
| **`DataPoint.java`** | Immutable Data Model | Encapsulates $(x, y)$ coordinate sets securely using strict encapsulation and encapsulation barriers. |
| **`GraphGenerator.java`** | Custom Rendering Core | Maps complex floating-point statistical boundaries directly to a 2D integer pixel matrix (`BufferedImage`). |

---

## 🧠 Statistical Modeling & Mathematics

The system processes multivariate continuous distributions to isolate predictive trends using the **Ordinary Least Squares (OLS)** framework.

### 📐 Implemented Algorithms
The engine loops through the coordinate arrays in linear $O(n)$ time complexity to calculate the fundamental components of linear modeling:

* **Slope ($m$):** Calculates the direction and intensity of the linear trend line:
    $$m = \frac{n\sum(xy) - \sum x \sum y}{n\sum(x^2) - (\sum x)^2}$$
* **Y-Intercept ($b$):** Locates the baseline cross-point where independent variables equal zero:
    $$b = \frac{\sum y - m\sum x}{n}$$
* **Pearson Correlation Coefficient ($r$):** Quantifies the linear strength and direction between variables ($[-1, 1]$ scalability).

---

## 📊 Sample Performance Output

### 🖥️ Console Diagnostics
When executed against the bundled `social_media_mental_health.csv` dataset, the tracking engine outputs the following real-time analytics:

```text
[SYSTEM INFO] Ingesting dataset... Parse Successful.
[SYSTEM INFO] Total valid entries cached: 450 records.
[ALGORITHM] Computing Ordinary Least Squares regression parameters...

==================================================
           MATHEMATICAL ENGINE RESULTS            
==================================================
  • Slope (m):              0.6432
  • Y-Intercept (b):       12.4819
  • Correlation (r):        0.7841 (Strong Positive)
==================================================

[RENDER ENGINE] Mapping coordinates to visual canvas...
[SUCCESS] Scatterplot saved successfully as 'scatterplot.png' [800x600].
