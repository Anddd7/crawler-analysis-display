package github.eddy.bigdata.douyu.crawler;

import github.eddy.bigdata.douyu.client.MessageWrapper;

/**
 * @author edliao
 */
public abstract class AbstractMessageHandler {

    public static final String MSG_TYPE = "type";


    private AbstractMessageHandler nextHandler;

    /**
     * 处理消息
     *
     * @param msg 消息内容map
     * @return 下一个handler需要处理的内容
     */
    public abstract MessageWrapper handle(MessageWrapper msg);

    /**
     * 传递给下一个handler
     */
    public void next(MessageWrapper msg) {
        MessageWrapper retMsg = handle(msg);
        if (retMsg != null && nextHandler != null) {
            nextHandler.next(retMsg);
        }
    }

    public AbstractMessageHandler appendHandler(AbstractMessageHandler messageHandler) {
        this.nextHandler = messageHandler;
        return this.nextHandler;
    }
}
