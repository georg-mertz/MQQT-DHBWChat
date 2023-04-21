import Chat.Chat;
import MQTTCom.Log;
import MQTTCom.Receiver;
import MQTTCom.Transmitter;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Log connectionLog = new Log();
        Chat chat = new Chat(connectionLog);
        chat.start();

        while (chat.checkIfMessageAvailable()) {
        }

        chat.stop();

        System.out.println("Log:");
        connectionLog.show();

        // Simulate Communication
        //Main main = new Main();
        //main.simulateCommunication(chat);

    }

    private void simulateCommunication(Chat chat) throws InterruptedException {
        Log log = new Log();
        Receiver receiver = new Receiver("10.50.12.150","/aichat/default", chat, log);
        Thread receiverThread = new Thread(receiver);               //Dank Async Task in run() wäre ein extra Thread nicht zwingend notwendig.
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

        System.out.println("Log:");
        log.show();
    }
}
