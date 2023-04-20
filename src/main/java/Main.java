public class Main {
    public static void main(String[] args) {
        Communicator communicator = new Communicator("10.50.12.150","/aichat/default","SenderName");
        System.out.println(communicator.connect());
        System.out.println(communicator.sendMessage("TestMessage"));
        System.out.println(communicator.disconnect());
    }
}
