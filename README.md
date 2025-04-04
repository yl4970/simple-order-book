# Limit mypackage.Order Book

This project implements the core logic for an **order book**, focusing on efficient data structures to manage and process orders in a trading system.

### Overview
An order book is a fundamental data structure in financial markets, where it holds a list of buy and sell orders for a given asset. This project simulates an order book by efficiently handling orders, matching them, and providing a simple interface for placing, modifying, and removing orders.

### Features
- **mypackage.Order Management**: Add, modify, and remove orders from the order book.
- **Price-Time Priority**: The order book respects price-time priority when matching orders.
- **Efficient Data Structures**: Implemented using appropriate data structures to ensure fast retrieval and updates.
- **Simple Interface**: Provides a straightforward interface to interact with the order book, including placing buy/sell orders.

### Project Structure
- **mypackage.Receiver.OrderBook**: The central class that handles the storage and management of orders.
- **mypackage.Order**: Represents individual buy or sell orders, including fields like price, quantity, and order type.
- **Receiver (Market Data Receiver)**: A Python-based receiver that simulates streaming market data into the order book.
- **Config**: Configuration files to manage connection details and API keys.

```bash
order_book/
│── data/
│     ├──market_data.json
│ 
│── databento_py/
│         ├── main.py
│         ├── market_data_server.py
│         ├── settings.py
│ 
│── src/main/java/mypackage/
│                   ├── Config.java
│                   ├── Receiver.java
│                   ├── OrderBook.java
│                   ├── Order.java
│── .env
│── pom.xml
│── README.md
│── .gitignore
```

### Requirements
To run this project, you'll need:
- **Java** (JDK 8 or higher)
- **Python** (for market data simulation)
- **Databento API Key** (for market data retrieval)

You may also need to install the following libraries:
- `dotenv` for managing environment variables (Java)
- `org.json` for JSON handling (Java)

### Setup Instructions

#### 1. **Clone the Repository**
```bash
git clone https://github.com/yourusername/simple-order-book.git
cd simple-order-book
```

#### 2. **Java Setup**

Ensure that you have Java installed, and add any necessary dependencies to your pom.xml for libraries such as org.json and dotenv.

#### 3. **Python Setup**

The Python part of the project interacts with the market data and streams it into the Java-based order book. To set up the Python environment:
```bash
# Install required Python packages
pip install -r requirements.txt
```
Run the main script that initiates both Python data server and Java receiver:
```bash
python3 main.py
```
#### 4. **Environment Configuration**

Ensure you have a .env file containing the required API key and server host:
```bash
# sample inputs
SERVER_HOST=127.0.0.0
SERVER_PORT=1000
API_KEY=your_databento_api_key
```
#### **How It Works**

* Market Data Simulation: The Python server.py script streams market data to the Java order book using a socket connection.
* mypackage.Order Book Logic: The Java Receiver listens for incoming market data, processes the data, and places orders into the mypackage.Receiver.OrderBook based on the received information.

#### **Contribution** 

* If you’d like to contribute to this project, feel free to fork the repository, submit pull requests, and report issues.

#### **Acknolwedgement**
* The market data used in this project is provided by Databento.