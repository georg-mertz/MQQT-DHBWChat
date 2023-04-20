package MQTTCom;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class Transmitter {
    private final String broker;
    private final String defaultTopic;
    private final String sender;
    private final String clientID;
    private final String clientStateTopic = "/aichat/clientstate";
    private final String startMessage = "MQTT Chat-Client started";
    private final String stopmessage = "MQTT Chat-Client stopped";
    private final String lastWillMessage = "MQTT Chat-Client lost connection";
    private Mqtt5BlockingClient client;


    public Transmitter(String broker, String defaultTopic, String sender){

        this.broker = broker;
        this.defaultTopic = defaultTopic;
        this.sender = sender;
        this.clientID = generateGUID();
        connect();
        sendStartMessage();

    }
    public boolean connect(){
        try{
            System.out.println("Trying to Connect ...");
            client = Mqtt5Client.builder().identifier(clientID).serverHost(broker).buildBlocking();
            client.connectWith()
                    .willPublish()
                        .topic(clientStateTopic)
                        .qos(MqttQos.AT_LEAST_ONCE)
                        .payload(lastWillMessage.getBytes(StandardCharsets.UTF_8))
                        .applyWillPublish()
                    .send();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean sendMessage(String message){

        String JSONMessage = messageToJSONString(message);
        try{
            client.publishWith().topic(defaultTopic).qos(MqttQos.AT_LEAST_ONCE).payload(JSONMessage.getBytes(StandardCharsets.UTF_8)).send();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean disconnect(){
        client.publishWith().topic(clientStateTopic).qos(MqttQos.AT_LEAST_ONCE).payload(stopmessage.getBytes(StandardCharsets.UTF_8)).send();
        client.disconnect();
        return true;
    }
    private String messageToJSONString(String message){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sender",sender);
        jsonObject.put("text",message);
        jsonObject.put("clientId",clientID);
        jsonObject.put("topic", defaultTopic);
        return jsonObject.toString();

    }

    private String generateGUID(){
        return UUID.randomUUID().toString();
    }
    private void sendStartMessage(){
        client.publishWith().topic(clientStateTopic).qos(MqttQos.AT_LEAST_ONCE).payload(startMessage.getBytes(StandardCharsets.UTF_8)).send();
    }


}
