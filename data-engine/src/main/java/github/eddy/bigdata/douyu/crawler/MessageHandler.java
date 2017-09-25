package github.eddy.bigdata.douyu.crawler;

import java.util.Map;

public abstract class MessageHandler {

    MessageHandler nextHandler;

    /**
     * 处理信息
     */
    public abstract Map<String, Object> handle(Map<String, Object> msg);

    /**
     * 传递给下一个handler
     */
    public void next(Map<String, Object> msg) {
        Map<String, Object> retMsg = handle(msg);
        if (nextHandler != null) {
            next(retMsg);
        }
    }
}
