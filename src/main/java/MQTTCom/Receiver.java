package MQTTCom;

import Chat.Chat;
import Chat.IMessageDisplay;
import Chat.Message;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import org.json.JSONException;
import org.json.JSONObject;
import java.nio.charset.StandardCharsets;

public class Receiver implements Runnable{
    private final String broker;
    private final String default_topic;
    private Mqtt5BlockingClient client;

    private final Chat chat;
    private final IMessageDisplay messageDisplay;
    private final Log log;

    public Receiver(String broker, String defaultTopic, Chat chat, IMessageDisplay messageDisplay, Log log) {
        this.broker = broker;
        default_topic = defaultTopic;
        this.chat = chat;
        this.messageDisplay = messageDisplay;
        this.log = log;
    }


    public void run() {
        if(!connect()){
            log.log("Receiver connection failed");
            return;
        }
        if(!subscribe()){
            log.log("Receiver subscribe failed");
            return;
        }

        client.toAsync().
                subscribeWith().
                topicFilter(default_topic).
                qos(MqttQos.AT_LEAST_ONCE).
                callback(mqtt5Publish -> {
                    String json = new String(mqtt5Publish.getPayloadAsBytes(),StandardCharsets.UTF_8);
                    if(isMessageValid(json)){
                        try {
                            Message message = new Message(json);
                            messageDisplay.display(message);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }else{
                        log.log("Received invalid json: \n"+json);
                    }
                }).
                send();

    }
    private boolean isMessageValid(String message){
        try{
            JSONObject jsonObject = new JSONObject(message);
            jsonObject.get("sender");
            jsonObject.get("clientId");
            jsonObject.get("topic");
            jsonObject.get("text");
            return true;
        }catch (JSONException err){
            return false;
        }
    }

    private boolean connect(){
        try{
            client = Mqtt5Client.builder().serverHost(broker).buildBlocking();
            client.connect();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    private boolean subscribe(){
        try{
            client.subscribeWith().topicFilter(default_topic).qos(MqttQos.EXACTLY_ONCE).send();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public void stop(){
        try{
            client.unsubscribeWith().topicFilter(default_topic).send();
            client.disconnect();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
