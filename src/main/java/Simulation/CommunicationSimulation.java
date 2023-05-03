package Simulation;

import Chat.Chat;
import MQTTCom.Log;
import MQTTCom.Receiver;
import MQTTCom.Transmitter;
import Chat.IMessageDisplay;

public class CommunicationSimulation {
    public void simulateCommunication(Chat chat, IMessageDisplay messageDisplay) throws InterruptedException {
        Log log = new Log();
        Receiver receiver = new Receiver("10.50.12.150","/aichat/default", chat, messageDisplay, log);
        receiver.run();
        Transmitter transmitter = new Transmitter("10.50.12.150","/aichat/default","SenderName", log);
        System.out.println("Connect");
        System.out.println(transmitter.connect());
        System.out.println("Send Message");
        System.out.println(transmitter.sendMessage("TestMessage"));
        Thread.sleep(20000);
        System.out.println("Disconnect");
        System.out.println(transmitter.disconnect());
        receiver.stop();

        System.out.println("Receiver/Transmitter log:");
        log.show();
    }
}
