package github.eddy.bigdata.douyu.crawler;

import com.alibaba.fastjson.JSON;
import github.eddy.bigdata.douyu.client.MessageWrapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author edliao
 */
@Slf4j
public class BulletMessageHandler extends AbstractMessageHandler {
    @Override
    public MessageWrapper handle(MessageWrapper msg) {
        //弹幕消息
        if ("chatmsg".equals(msg.getType())) {
            log.debug("收到弹幕消息[{}]:\n{}", msg.get("roomId"), JSON.toJSONString(msg));
            return null;
        }
        return msg;
    }
}
