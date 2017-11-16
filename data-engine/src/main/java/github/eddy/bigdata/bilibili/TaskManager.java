package github.eddy.bigdata.bilibili;


import github.eddy.bigdata.bilibili.service.AbstractService;
import github.eddy.bigdata.bilibili.service.common.TaskParams;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * bilibili相关任务的启动和管理
 *
 * @author edliao
 * @implNote 把所有分析和抓取(需要耗时的)的操作都作为一个一个任务 ,每个任务的执行都有相应的记录来避免重复执行和查错redo
 */
@Slf4j
@Component
public class TaskManager {

  @Autowired
  private ApplicationContext applicationContext;

  /**
   * 执行任务
   *
   * @param serviceName 模块
   * @param taskName 任务名
   * @param taskParams 参数
   */
  public void execute(String serviceName, String taskName, TaskParams taskParams) {
    AbstractService service = applicationContext
        .getBean(serviceName + "Service", AbstractService.class);
    if (service == null) {
      log.debug("指定的{}模块不存在", serviceName);
      return;
    }
    service.execute(taskName, taskParams);
  }

  /**
   * TODO for test
   */
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

  @Scheduled(fixedRate = 3000)
  public void reportCurrentTime() {
    System.out.println("现在时间：" + dateFormat.format(new Date()));
  }
}