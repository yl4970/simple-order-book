import subprocess
import time
from settings import *

# Path to the Python script for the market data sender (make sure the path is correct)
python_server_script = PYTHON_DATA_SERVER_DIR

# Command to run the Java receiver
java_receiver_command = [
    "java",
    "-cp",
    f"{JAVA_COMPLIED_CLASS_DIR}:{PATH_TO_DOTENV_JAR}:{PATH_TO_JSON_JAR}",
    "mypackage.Receiver"
]

# Start the Python server (it will run asynchronously), suppressing its output
python_process = subprocess.Popen(
    ["python3", python_server_script],
    stdout=subprocess.DEVNULL,  # Suppress Python stdout
    stderr=subprocess.DEVNULL   # Suppress Python stderr
)

# Wait briefly to ensure the Python server is ready
time.sleep(2)

# Start the Java receiver (it will also run asynchronously), capturing both stdout and stderr
java_process = subprocess.Popen(
    java_receiver_command,
    stdout=subprocess.PIPE,  # Capture Java stdout
    stderr=subprocess.PIPE   # Capture Java stderr
)

# Read Java output (logs) and print it in real-time
try:
    while True:
        # Read Java stdout
        output = java_process.stdout.readline()
        if output == b"" and java_process.poll() is not None:
            break
        if output:
            print(f"Java Log: {output.decode().strip()}")

        # Read Java stderr
        error_output = java_process.stderr.readline()
        if error_output:
            print(f"Java Error: {error_output.decode().strip()}")

except KeyboardInterrupt:
    print("Stopping both processes...")

    # Gracefully terminate the Python and Java processes
    python_process.terminate()
    java_process.terminate()

    # Optionally wait for the processes to end (it might take a moment)
    python_process.wait()
    java_process.wait()
    print("Both processes terminated.")