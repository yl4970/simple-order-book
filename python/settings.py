import os
from dotenv import load_dotenv

# Load environment variables from .env
load_dotenv()

# Get the API key
API_KEY = os.getenv("API_KEY")

# Get path
python_data_server_path = os.getenv("python_data_server_path")
java_complied_class_path = os.getenv("java_complied_class_path")
