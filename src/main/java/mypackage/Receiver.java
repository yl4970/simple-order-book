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

        while (true) {

            try (Socket socket = new Socket(HOST, PORT);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                System.out.println("Waiting for connection...");

                // Listen for incoming data
                String incomingData;
                while ((incomingData = reader.readLine()) != null) {
                    // Process data into an mypackage.Order object
                    processData(incomingData, ob);

                    // Print the parsed order (or update your order book with this data)
                    // System.out.println(incomingData);
                }
                break;

            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        System.out.println(ob.queueMap);
    }

    private static void processData(String data, mypackage.OrderBook ob) {
        // Parse the incoming JSON data
        JSONObject json = new JSONObject(data);

        // Extract order details from JSON
        String action = json.getString("action");
        Order.side side = (Objects.equals(json.getString("side"), "B")) ? Order.side.BID : Order.side.ASK;
        double price = json.getDouble("price");
        double shares = json.getDouble("shares");

        // Place order to order book
        Order order = new Order(side, price, shares);
        order.place(ob);
    }
}

