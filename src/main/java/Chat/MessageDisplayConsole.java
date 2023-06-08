package Chat;

public class MessageDisplayConsole implements IMessageDisplay {
    @Override
    public void display(Message message) {
        System.out.println(message);
    }
}
