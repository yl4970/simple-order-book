import os
from dotenv import load_dotenv

# Load environment variables from .env
load_dotenv()

# Get the API key
API_KEY = os.getenv("API_KEY")

# Get server host and port
SERVER_HOST = os.getenv("SERVER_HOST")
SERVER_PORT = int(os.getenv("SERVER_PORT"))

# Get path
PYTHON_DATA_SERVER_DIR = os.getenv("PYTHON_DATA_SERVER_DIR")
JAVA_COMPLIED_CLASS_DIR = os.getenv("JAVA_COMPLIED_CLASS_DIR")
