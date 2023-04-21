package Chat;

import MQTTCom.Log;
import MQTTCom.Receiver;
import MQTTCom.Transmitter;

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
        System.out.println("To send a message, just type it into the console and hit ENTER.");

        // Start receiver threat
        receiver = new Receiver(broker, defaultTopic, this, connectionLog);
        receiver.run();
        
        // Scan for message
        Scanner scanner = new Scanner(System.in);
        String message = scanner.nextLine();
        send(message);
    }
    
    public void stop() {
        receiver.stop();
    }

    public void parseAndDisplay(String message) {
        System.out.println(message);
    }

    private void send(String message) {
        Transmitter transmitter = new Transmitter(broker, defaultTopic, sender, connectionLog);
        transmitter.connect();
        transmitter.sendMessage(message);
        transmitter.disconnect();
    }
}

