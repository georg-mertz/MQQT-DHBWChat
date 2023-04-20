import MQTTCom.Receiver;
import MQTTCom.Transmitter;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Main main = new Main();
        main.simulateCommunication();
    }
    private void simulateCommunication() throws InterruptedException {
        Receiver receiver = new Receiver("10.50.12.150","/aichat/default");
        Thread receiverThread = new Thread(receiver);
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
