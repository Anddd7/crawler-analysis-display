package github.eddy.bigdata.douyu;

import github.eddy.bigdata.douyu.client.DyBulletScreenClient;
import github.eddy.bigdata.douyu.crawler.BulletMessageHandler;
import github.eddy.bigdata.douyu.crawler.GiftMessageHandler;
import github.eddy.bigdata.douyu.crawler.AbstractMessageHandler;
import github.eddy.bigdata.douyu.crawler.OtherMessageHandler;
import github.eddy.bigdata.douyu.thread.KeepAlive;
import github.eddy.bigdata.douyu.thread.KeepGetMsg;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dy
 * @author edliao
 * 连接管理
 * 三个线程 -> 心跳连接;获取消息;处理消息;
 */
public class DyBulletClientManager {
    /**
     * 弹幕池分组号，海量模式使用-9999
     */
    private static final int HUGE_GROUP_ID = -9999;

    /**
     * 弹幕连接池
     */
    @Getter
    Map<Integer, DyBulletScreenClient> clientPool = new ConcurrentHashMap<>();

    private KeepGetMsg keepGetMsg = new KeepGetMsg(this);
    private KeepAlive keepAlive = new KeepAlive(this);

    public DyBulletClientManager connectRoom(AbstractMessageHandler messageHandler, int... roomIds) {
        for (int roomId : roomIds) {
            connectRoom(messageHandler, roomId);
        }
        return this;
    }

    public DyBulletScreenClient connectRoom(AbstractMessageHandler messageHandler, int roomId) {
        DyBulletScreenClient client = new DyBulletScreenClient(messageHandler, roomId, HUGE_GROUP_ID);
        clientPool.put(roomId, client);
        return client;
    }

    public void start() {
        keepAlive.start();
        keepGetMsg.start();
    }

    public void shutdown() {
        keepAlive.setRunning(false);
        keepGetMsg.setRunning(false);
        clientPool.clear();
    }

    public static void main(String[] s) throws InterruptedException {
        AbstractMessageHandler rootHandler = new BulletMessageHandler();
        rootHandler.appendHandler(new GiftMessageHandler())
                .appendHandler(new OtherMessageHandler());

        DyBulletClientManager manager = new DyBulletClientManager();
        manager.start();

        manager.connectRoom(rootHandler, 635099);
        manager.connectRoom(rootHandler, 7911);
    }
}
