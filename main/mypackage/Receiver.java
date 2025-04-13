package mypackage;

import java.io.*;
import java.net.*;
import java.util.*;

import org.json.JSONObject;

public class Receiver {
    private static final int PORT = Config.PORT;  // The port Python is sending data to
    private static final String HOST = Config.HOST; // Localhost

    public static void main(String[] args) {
        OrderBook ob = new OrderBook();

        try (Socket socket = new Socket(HOST, PORT);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Waiting for connection...");
            System.out.flush();

            // Continuously listen for incoming data
            String incomingData;
            while ((incomingData = reader.readLine()) != null) {
                // Process data into a mypackage.Order object
                processData(incomingData, ob);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(ob.queueMap);
    }

    private static void processData(String data, mypackage.OrderBook ob) {
        // Parse the incoming JSON data
        JSONObject json = new JSONObject(data);

        // Pass when non-effect order type flows in
        if (
            Objects.equals(json.getString("action"), "T") || // An aggressing order traded. Does not affect the book.
            Objects.equals(json.getString("action"), "F") || // A resting order was filled. Does not affect the book.
            Objects.equals(json.getString("action"), "N") || // No action: does not affect the book, but may carry flags or other information.

            // Unable to implement the logic to cancel order as order_id is unable to specified in realtime datafeed
            Objects.equals(json.getString("action"), "C")) // Fully or partially cancel an order from the book
        {} // pass

        // Place order to order book if action == "A" // Insert a new order into the book.
        else if (Objects.equals(json.getString("action"), "A")) {
            if (Objects.equals(json.getString("side"), "B")) {
                Order order = new Order(
                        Order.side.BID,
                        json.getDouble("bid_shares"),
                        json.getDouble("bid_price")
                );
                order.place(ob);
            } else {
                Order order = new Order(
                        Order.side.ASK,
                        json.getDouble("ask_shares"),
                        json.getDouble("ask_price")
                );
                order.place(ob);
            }
        }
    }
}

