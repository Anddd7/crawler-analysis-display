package github.eddy.bigdata.douyu.client;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;
import java.util.Optional;

/**
 * @author edliao
 */
@Getter
@Setter
@ToString
public class MessageWrapper {
    String type;
    Map<String, Object> params;

    public static MessageWrapper wrapper(Map<String, Object> message) {
        MessageWrapper messageWrapper = new MessageWrapper();
        messageWrapper.type = (String) Optional.ofNullable(message.remove("type")).orElse(null);
        messageWrapper.params = message;
        return messageWrapper;
    }

    public Object get(String key){
        return params.get(key);
    }
}
