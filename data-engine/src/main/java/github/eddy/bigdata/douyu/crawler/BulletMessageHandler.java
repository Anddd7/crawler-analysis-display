package github.eddy.bigdata.douyu.crawler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class BulletMessageHandler extends MessageHandler {

    @Override
    public Map<String, Object> handle(Map<String, Object> msg) {
        if (msg.get("type").equals("chatmsg")) {//弹幕消息
            log.debug("收到弹幕消息[{}]:\n{}", msg.get("roomId"), JSON.toJSONString(msg));
            return null;
        }
        return msg;
    }
}
