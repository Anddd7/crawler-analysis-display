package github.eddy.bigdata.douyu.crawler;

import com.alibaba.fastjson.JSON;
import github.eddy.bigdata.douyu.client.MessageWrapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author edliao
 */
@Slf4j
public class OtherMessageHandler extends AbstractMessageHandler {

    @Override
    public MessageWrapper handle(MessageWrapper msg) {
        log.debug("收到消息[{}]:\n{}", msg.get("roomId"), JSON.toJSONString(msg));
        return null;
    }
}