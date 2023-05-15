import Chat.Chat;
import Chat.IMessageDisplay;
import Chat.MessageDisplayConsole;
import MQTTCom.Log;

import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Log connectionLog = new Log();
        IMessageDisplay messageDisplay = new MessageDisplayConsole();

        System.out.print("Name: ");
        Scanner scanner = new Scanner(System.in);
        String sender = scanner.nextLine();

        Chat chat = new Chat(connectionLog, sender, messageDisplay);
        chat.start();

        boolean quitChat = false;
        while (!quitChat) {
            String message = scanner.nextLine();

            if (Objects.equals(message, "q")) {
                quitChat = true;
            } else {
                chat.send(message);
            }
        }

        chat.stop();

        System.out.println("Log:");
        connectionLog.show();

    }
}
