package github.eddy.bigdata.douyu.crawler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class OtherMessageHandler extends MessageHandler {

    @Override
    public Map<String, Object> handle(Map<String, Object> msg) {
        log.debug("收到消息[{}]:\n{}", msg.get("roomId"), JSON.toJSONString(msg));
        return null;
    }
}