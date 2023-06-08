package MQTTCom;

import java.util.Stack;

public class Log {

    private final Stack<String> logs;

    public Log() {
        logs = new Stack<>();
    }

    public void log(String message) {
        logs.push(message);
    }

    public void show() {
        for (String logMessage: logs) {
            System.out.println(logMessage);
        }
    }
}
