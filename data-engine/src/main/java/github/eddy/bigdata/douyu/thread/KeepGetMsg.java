package github.eddy.bigdata.douyu.thread;

import github.eddy.bigdata.douyu.DyBulletClientManager;
import github.eddy.bigdata.douyu.client.DyBulletScreenClient;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author edliao
 * @author FerroD
 * @implNote 获取服务器弹幕信息线程
 * @implNote 单例变为注入
 */
@Slf4j
public class KeepGetMsg extends Thread {
    DyBulletClientManager manager;

    public KeepGetMsg(DyBulletClientManager manager) {
        super("弹幕消息监听Thread");
        this.manager = manager;
    }

    @Getter
    @Setter
    Boolean running = true;

    @Override
    public void run() {
        log.debug("Get message start->");
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        while (running) {
            for (DyBulletScreenClient client : manager.getClientPool().values()) {
                if (client.getReadyFlag()) {
                    executorService.execute(client::getServerMsg);
                }
            }
        }
        executorService.shutdown();
    }
}
