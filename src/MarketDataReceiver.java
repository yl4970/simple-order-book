import java.io.*;
import java.net.*;

public class MarketDataReceiver {
    public static void main(String[] args) {
        String host = "127.0.0.1";  // Match Python server address
        int port = 5001;

        try (Socket socket = new Socket(host, port);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Connected to Python server. Listening for real-time data...");

            String data;
            while (true) {
                data = reader.readLine();  // This will block until a new line is received
                if (data != null) {
                    System.out.println("Received: " + data);
                } else {
                    // Connection closed or stream ends
                    System.out.println("Connection closed or no data received.");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}