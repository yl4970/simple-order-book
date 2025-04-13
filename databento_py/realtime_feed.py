import time
import databento as db
import numpy as np
import signal
import sys
from settings import *

client = db.Live(API_KEY)


data = client.subscribe(
    dataset="EQUS.MINI",
    schema="mbp-1",
    symbols="AAPL",
    start="2025-04-10T09:30:00" ## used for live replay
)


def message(data):

    msg ={
        "action":data.action,
        "side": data.side,
        "bid_price": data.levels[0].bid_px/1e9,
        "bid_shares": data.levels[0].bid_sz,
        "ask_price": data.levels[0].ask_px/1e9,
        "ask_shares": data.levels[0].ask_sz
    }

    print(msg)


# We add a print callback to view each record
client.add_callback(message)


# Graceful exit on Ctrl+C
def signal_handler(sig, frame):
    print("\nExiting gracefully...")
    client.terminate()  # Stop the Databento client connection
    print("Connection still open?",client.is_connected())
    sys.exit(0)  # Exit the program

# Register the signal handler
signal.signal(signal.SIGINT, signal_handler)

# Start the live client to begin data streaming
client.start()

# Run the stream for 15 seconds before closing
print("Press Ctrl+C to stop...")
client.block_for_close(timeout=30)  # Keep the connection open indefinitely