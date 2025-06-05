# 📈 Stock Market Simulator

This project implements a Java-based stock market simulator that allows users to simulate buying and selling fictitious assets using real-world financial data APIs. It features an interactive graphical user interface, price tracking charts, and portfolio management tools.

---

## 🔍 Project Structure

- `src/`: Main application source code  
  &nbsp;&nbsp;&nbsp;&nbsp;- `controller/`: Event handling and logic coordination  
  &nbsp;&nbsp;&nbsp;&nbsp;- `main/`: Main launcher and application entry  
  &nbsp;&nbsp;&nbsp;&nbsp;- `model/`: Core domain classes (e.g., `CompteBancaire`, `Portefeuille`)  
  &nbsp;&nbsp;&nbsp;&nbsp;- `service/`: Notification and data services  
  &nbsp;&nbsp;&nbsp;&nbsp;- `ui/`: GUI components  
  &nbsp;&nbsp;&nbsp;&nbsp;- `user/`: User registration and login handling  
- `lib/`: External `.jar` dependencies
- `photos/`: Screenshots of the simulator's functionality and interface
- `reports/`: Final project reports (French and English)

---

## 🧠 Implemented Features

- Real-time financial data display and charting  
- Interactive portfolio tracking (buy/sell operations)  
- Price visualization with moving averages and Bollinger bands  
- Dynamic UI updates and user feedback  
- User login and registration with persistence  
- Notifications for stock price changes or errors

---

## 📸 Screenshots

- `buy_notification_tesla.png`: Purchase confirmation popup  
- `login_popup.png`: Login form interface  
- `register_popup.png`: Sign-up window  
- `price_alert_tesla_drop.png`: Tesla stock price drop alert  
- `search_error_invalid_symbol.png`: Error when symbol not found  
- `search_result_mcdonalds.png`: Successful data retrieval for McDonald's  
- `search_result_nvda.png`: Loaded chart for NVIDIA stock  
- `empty_chart_no_symbol.png`: UI state before any stock is selected

---

## 🛠️ Requirements

- Java 8 or higher
- External Libraries:
  - `gson-2.13.1.jar`
  - `jfreechart-1.5.3.jar`
  - `json-20250107.jar`
  - `jcommon-1.0.24.jar`

(Located in the `lib/` directory)

---

## 📄 Reports

- 🇫🇷 French report: [`reports/Rapport.pdf`](reports/Rapport.pdf)  
- 🇬🇧 English report: [`reports/Report.pdf`](reports/Report.pdf)

---

## 📈 Author

**Ralph Khairallah**  
ENSEEIHT 2024–2025

