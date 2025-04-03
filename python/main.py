import subprocess
import time
from settings import python_data_server_path, java_complied_class_path


# Path to the Python script for the market data sender (make sure the path is correct)
python_server_script = java_complied_class_path

# Command to run the Java receiver
java_receiver_command = ["java",
                         "-cp",
                         python_data_server_path,
                         "MarketDataReceiver"]

# Start the Python server (it will run asynchronously)
python_process = subprocess.Popen(["python3", python_server_script])

# Start the Java receiver (it will also run asynchronously)
java_process = subprocess.Popen(java_receiver_command)

# Wait for both processes to finish (you can adjust this if needed)
try:
    while True:
        time.sleep(1)  # Keep the script running while both processes are active
except KeyboardInterrupt:
    print("Stopping both processes...")

    # Gracefully terminate the Python and Java processes
    python_process.terminate()
    java_process.terminate()

    # Optionally wait for the processes to end (it might take a moment)
    python_process.wait()
    java_process.wait()
    print("Both processes terminated.")