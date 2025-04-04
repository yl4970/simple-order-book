import java.io.*;
import java.net.*;
import org.json.JSONObject;

public class Receiver {
        private static final int PORT = Config.PORT;  // The port Python is sending data to
        private static final String HOST = Config.HOST; // Localhost
        private OrderBook ob = new OrderBook();

    public void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Waiting for connection...");
            Socket socket = serverSocket.accept();
            System.out.println("Connection established with " + socket.getInetAddress());

            // Input stream to read data from Python
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Listen for incoming data
            String incomingData;
            while ((incomingData = reader.readLine()) != null) {
                // Process data into an Order object
                processData(incomingData);

                // Print the parsed order (or update your order book with this data)
                System.out.println(incomingData);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processData(String data) {
        // Parse the incoming JSON data
        JSONObject json = new JSONObject(data);

        // Extract order details from JSON
        String action = json.getString("action");
        Order.side side = (json.getString("side")=="B") ? Order.side.BID : Order.side.ASK;
        double price = json.getDouble("price");
        double shares = json.getDouble("shares");

        // Place order to order book
        Order order = new Order(side, price, shares);
        order.place(this.ob);
    }

}
