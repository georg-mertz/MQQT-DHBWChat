package Simulation;

import Chat.Chat;
import MQTTCom.Log;

public class Simulation {
    public static void main(String[] args) throws InterruptedException {
        // Simulate Communication
        Log connectionLog = new Log();
        String senderName = "SenderName";
        Chat chat = new Chat(connectionLog, senderName);
        CommunicationSimulation communicationSimulation = new CommunicationSimulation();

        communicationSimulation.simulateCommunication(chat);
    }
}
