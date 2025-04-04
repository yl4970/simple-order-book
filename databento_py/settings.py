import os
from datetime import datetime
from dotenv import load_dotenv

NOW = datetime.now().strftime("%Y-%m-%dT%H:%M:%S")

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
PATH_TO_DOTENV_JAR = os.getenv("PATH_TO_DOTENV_JAR")
PATH_TO_JSON_JAR = os.getenv("PATH_TO_JSON_JAR")