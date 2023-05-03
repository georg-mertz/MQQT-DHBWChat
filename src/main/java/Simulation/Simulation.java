package Simulation;

import Chat.Chat;
import Chat.IMessageDisplay;
import Chat.MessageDisplayConsole;
import MQTTCom.Log;

public class Simulation {
    public static void main(String[] args) throws InterruptedException {
        // Simulate Communication
        Log connectionLog = new Log();
        IMessageDisplay messageDisplay = new MessageDisplayConsole();
        String senderName = "SenderName";
        Chat chat = new Chat(connectionLog, senderName, messageDisplay);
        CommunicationSimulation communicationSimulation = new CommunicationSimulation();

        communicationSimulation.simulateCommunication(chat, messageDisplay);
    }
}
