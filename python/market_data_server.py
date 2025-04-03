import time
import databento as db
import numpy as np
import signal
import sys
import socket
from settings import API_KEY

# Socket setup
HOST = '127.0.0.1'  # Localhost
PORT = 5001         # Same port Java will connect to
server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.bind((HOST, PORT))
server_socket.listen(1)

print(f"Waiting for Java connection on {HOST}:{PORT}...")
conn, addr = server_socket.accept()
print(f"Connected by {addr}")

# Setup the client and subscribe to data
client = db.Live(API_KEY)

# Subscribe to the data stream and continuously process it
data = client.subscribe(
    dataset="EQUS.MINI",
    schema="mbp-1",
    symbols="AAPL",
    start="2025-04-02T09:30:00"  # Used for live replay
)

def stream(data):
    if data.side == "B":
        msg = f"{data.action} @{(data.levels[0].bid_px)/1000000000} {data.levels[0].bid_sz} shares."
    else: msg = f"{data.action} @{data.levels[0].ask_px/1000000000} {data.levels[0].ask_sz} shares."
    conn.sendall(msg.encode())  # Send data to Java

client.add_callback(stream)

# Graceful exit on Ctrl+C
def signal_handler(sig, frame):
    print("\nExiting gracefully...")
    client.terminate()  # Stop the Databento client connection
    print("Connection still open?",client.is_connected())
    conn.close()
    server_socket.close()
    sys.exit(0)  # Exit the program

# Register the signal handler
signal.signal(signal.SIGINT, signal_handler)

# Start the live client to begin data streaming
client.start()

# Run the stream for 15 seconds before closing
print("Press Ctrl+C to stop...")
client.block_for_close(timeout=30)  # Keep the connection open indefinitely


