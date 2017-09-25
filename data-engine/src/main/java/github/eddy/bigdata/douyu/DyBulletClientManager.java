package github.eddy.bigdata.douyu;

import github.eddy.bigdata.douyu.client.DyBulletScreenClient;
import github.eddy.bigdata.douyu.thread.KeepAlive;
import github.eddy.bigdata.douyu.thread.KeepGetMsg;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 连接管理
 * 三个线程 -> 心跳连接;获取消息;处理消息;
 */
public class DyBulletClientManager {
    private static final int HUGE_GROUP_ID = -9999;//弹幕池分组号，海量模式使用-9999

    @Getter
    Map<Integer, DyBulletScreenClient> clientPool = new ConcurrentHashMap<>();//弹幕连接池

    KeepGetMsg keepGetMsg = new KeepGetMsg(this);
    KeepAlive keepAlive = new KeepAlive(this);

    public DyBulletScreenClient connectRoom(int roomId, int groupId) {
        return connectRoom(roomId, groupId);
    }

    public DyBulletScreenClient connectRoom(int roomId) {
        DyBulletScreenClient client = new DyBulletScreenClient(roomId, HUGE_GROUP_ID);

        clientPool.put(roomId, client);
        return client;
    }

    public void start() {
        keepAlive.start();
        keepGetMsg.start();
    }

    public static void main(String[] s) throws InterruptedException {
        DyBulletClientManager manager = new DyBulletClientManager();
        manager.start();

        Thread.sleep(5 * 1000);

        DyBulletScreenClient client1 = manager.connectRoom(635099);
    }
}
