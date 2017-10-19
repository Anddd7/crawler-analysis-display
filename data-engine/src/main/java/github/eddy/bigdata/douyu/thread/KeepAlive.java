package github.eddy.bigdata.douyu.thread;

import github.eddy.bigdata.douyu.DyBulletClientManager;
import github.eddy.bigdata.douyu.client.DyBulletScreenClient;
import github.eddy.common.ThreadTools;
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
            manager.getClientPool().values().stream()
                    .filter(DyBulletScreenClient::getReadyFlag)
                    //发送心跳保持协议给服务器端
                    .forEach(DyBulletScreenClient::keepAlive);
            //设置间隔45秒再发送心跳协议
            ThreadTools.sleep(45000);
        }
    }
}
