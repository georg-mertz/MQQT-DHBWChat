package Chat;

import MQTTCom.Log;
import MQTTCom.Receiver;
import MQTTCom.Transmitter;

public class Chat implements IChat {

    private final String broker;
    private final String defaultTopic;
    private final String sender;
    private final IMessageDisplay messageDisplay;

    private final Log connectionLog;
    private Receiver receiver;

    public Chat(Log connectionLog, String sender, IMessageDisplay messageDisplay) {
        broker = Configuration.instance.Broker;
        defaultTopic = Configuration.instance.defaultTopic;
        this.connectionLog = connectionLog;
        this.sender = sender;
        this.messageDisplay = messageDisplay;

        if(sender == null || sender.trim().isEmpty()) {
            throw new IllegalArgumentException("Sender cannot be null or empty!");
        }
    }

    public void start() {
        // Start receiver threat
        receiver = new Receiver(broker, defaultTopic, this, messageDisplay, connectionLog);
        receiver.run();

        System.out.println("To send a message, just type it into the console and hit ENTER.");
    }

    public void send(String message) {
        Transmitter transmitter = new Transmitter(broker, defaultTopic, sender, connectionLog);
        transmitter.connect();
        transmitter.sendMessage(message);
        transmitter.disconnect();
    }
    
    public void stop() {
        receiver.stop();
    }
}

