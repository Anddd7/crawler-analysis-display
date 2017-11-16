package github.eddy.bigdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author edliao
 * @since 11/16/2017 使用spring进行项目的启动和定时任务的控制 ,方便后续的远程接口集成
 */
@SpringBootApplication
@EnableScheduling
public class Application implements CommandLineRunner {

  @Autowired
  ApplicationContext context;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    /**
     * 可以加载一些初始配置 , 此时定时任务已经开始运作
     */
  }
}
