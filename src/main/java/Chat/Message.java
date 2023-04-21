package Chat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

public class Message {

    private String clientId;
    private String sender;
    private String topic;
    private String text;

    public Message(String json) throws Exception {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> properties = objectMapper.readValue(json, new TypeReference<>() {{ }});

            if (!properties.containsKey("sender") || !properties.containsKey("text") || !properties.containsKey("clientId") || !properties.containsKey("topic")) {
                throw new Exception("JSON is invalid");
            }

            sender = properties.get("sender");
            text = properties.get("text");
            clientId = properties.get("clientId");
            topic = properties.get("topic");

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return sender + ": " + text;
    }
}
