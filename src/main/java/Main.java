import Chat.Chat;
import MQTTCom.Receiver;
import MQTTCom.Transmitter;

import java.util.Scanner;

public class Main {

    private final String broker = "10.50.12.150";
    private final String defaultTopic = "/aichat/default";

    public static void main(String[] args) throws InterruptedException {

        Chat chat = new Chat();
        chat.start();
        chat.stop();

        // Simulate Communication
        //Main main = new Main();
        //main.simulateCommunication(chat);

    }

    private void simulateCommunication(Chat chat) throws InterruptedException {
        Receiver receiver = new Receiver("10.50.12.150","/aichat/default", chat);
        Thread receiverThread = new Thread(receiver);               //Dank Async Task in run() wäre ein extra Thread nicht zwingend notwendig.
        receiver.run();
        Transmitter transmitter = new Transmitter("10.50.12.150","/aichat/default","SenderName");
        System.out.println("Connect");
        System.out.println(transmitter.connect());
        System.out.println("Send Message");
        System.out.println(transmitter.sendMessage("TestMessage"));
        Thread.sleep(20000);
        System.out.println("Disconnect");
        System.out.println(transmitter.disconnect());
        receiver.stop();
    }
}
