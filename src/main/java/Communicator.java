
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class Communicator {
    private final String broker;
    private final String default_topic;
    private final String sender;
    private final String clientID;
    private Mqtt5BlockingClient client;

    public Communicator(String broker, String default_topic, String sender){

        this.broker = broker;
        this.default_topic = default_topic;
        this.sender = sender;
        this.clientID = generateGUID();
        connect();

    }
    public boolean connect(){
        try{
            System.out.println("Trying to Connect ...");
            client = Mqtt5Client.builder().identifier(clientID).serverHost(broker).buildBlocking();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean sendMessage(String message){

        String JSONMessage = messageToJSONString(message);
        try{
            client.connect();
            client.publishWith().topic(default_topic).qos(MqttQos.AT_LEAST_ONCE).payload(JSONMessage.getBytes(StandardCharsets.UTF_8)).send();
            //client.disconnect();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean disconnect(){
        client.disconnect();
        return true;
    }
    private String messageToJSONString(String message){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sender",sender);
        jsonObject.put("text",message);
        jsonObject.put("clientId",clientID);
        jsonObject.put("topic",default_topic);
        return jsonObject.toString();

    }

    private String generateGUID(){
        return UUID.randomUUID().toString();
    }
}
