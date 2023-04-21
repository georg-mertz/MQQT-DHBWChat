package Chat;

import MQTTCom.Log;
import MQTTCom.Receiver;
import MQTTCom.Transmitter;

import java.util.Objects;
import java.util.Scanner;

public class Chat implements IChat {

    private final String broker;
    private final String defaultTopic;
    private final String sender;


    private Log connectionLog;
    private Receiver receiver;

    public Chat(Log connectionLog) {
        broker = Configuration.instance.Broker;
        defaultTopic = Configuration.instance.defaultTopic;
        this.connectionLog = connectionLog;

        System.out.print("Name: ");
        Scanner scanner = new Scanner(System.in);
        sender = scanner.nextLine();

        if(sender == null || sender.trim().isEmpty()) {
            throw new IllegalArgumentException("Sender cannot be null or empty!");
        }
    }

    public void start() {
        // Start receiver threat
        receiver = new Receiver(broker, defaultTopic, this, connectionLog);
        receiver.run();

        System.out.println("To send a message, just type it into the console and hit ENTER.");
    }

    public boolean checkIfMessageAvailable() {
        // Scan for message
        Scanner scanner = new Scanner(System.in);
        String message = scanner.nextLine();

        if (Objects.equals(message, "q")) {
            return false;
        }

        send(message);

        return true;
    }
    
    public void stop() {
        receiver.stop();
    }

    public void parseAndDisplay(String json) {
        try {
            Message message = new Message(json);
            System.out.println(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void send(String message) {
        Transmitter transmitter = new Transmitter(broker, defaultTopic, sender, connectionLog);
        transmitter.connect();
        transmitter.sendMessage(message);
        transmitter.disconnect();
    }
}

