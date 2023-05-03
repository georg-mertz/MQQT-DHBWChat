import Chat.Chat;
import MQTTCom.Log;
import MQTTCom.Receiver;
import MQTTCom.Transmitter;

import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Log connectionLog = new Log();

        System.out.print("Name: ");
        Scanner scanner = new Scanner(System.in);
        String sender = scanner.nextLine();

        Chat chat = new Chat(connectionLog, sender);
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
