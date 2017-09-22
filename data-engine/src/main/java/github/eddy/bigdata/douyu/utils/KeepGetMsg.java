package github.eddy.bigdata.douyu.utils;

import github.eddy.bigdata.douyu.DyBulletClientManager;
import github.eddy.bigdata.douyu.client.DyBulletScreenClient;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;

import static java.time.Instant.now;


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
        while (running) {
            for (DyBulletScreenClient client : manager.getClientPool().values()) {
                if (client.getReadyFlag()) {
                    Instant start = now();
                    client.getServerMsg();
                    log.debug("获取弹幕耗时:{} ms", Duration.between(start, now()).toMillis());
                }
            }
        }
    }
}
