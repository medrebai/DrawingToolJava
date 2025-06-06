package logger;

public class ConsoleLogger implements LoggerStrategy {

    @Override
    public void log(String message) {
        System.out.println("[Console Logger] " + message);
    }
}
