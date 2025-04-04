import io.github.cdimascio.dotenv.Dotenv;

public class Config {
    // Load the .env file
    public static final Dotenv dotenv = Dotenv.load();

    // Access environment variables
    public static final String HOST = dotenv.get("SERVER_HOST");
    public static final int PORT = Integer.parseInt(dotenv.get("SERVER_PORT"));
}