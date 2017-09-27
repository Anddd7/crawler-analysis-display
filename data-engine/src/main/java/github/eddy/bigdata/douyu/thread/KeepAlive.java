package github.eddy.bigdata.douyu.thread;

import github.eddy.bigdata.douyu.DyBulletClientManager;
import github.eddy.bigdata.douyu.client.DyBulletScreenClient;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author edliao
 * @author FerroD
 * @implNote 服务器心跳保持线程
 * @implNote 单例变为注入
 */
@Slf4j
public class KeepAlive extends Thread {

    DyBulletClientManager manager;

    public KeepAlive(DyBulletClientManager manager) {
        super("心跳连接守护Thread");
        this.manager = manager;
    }

    @Getter
    @Setter
    Boolean running = true;

    @Override
    public void run() {
        log.debug("Keep alive start->");
        while (running) {
            for (DyBulletScreenClient client : manager.getClientPool().values()) {
                if (client.getReadyFlag()) {
                    //发送心跳保持协议给服务器端
                    client.keepAlive();
                }
            }
            try {
                //设置间隔45秒再发送心跳协议
                Thread.sleep(45000);//keep live at least once per minute
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
