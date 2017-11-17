package github.eddy.bigdata.bilibili;


import github.eddy.bigdata.bilibili.service.AbstractService;
import github.eddy.bigdata.bilibili.service.common.TaskParams;
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
   * TODO 抓取前一天的视频数据
   */
  @Scheduled(cron = "0 0 3 * * ? ")
  public void crawlerYesterday() {
  }

  /**
   * TODO 抓取上个月的视频数据 (重置数据 ,分析上个月的播放情况)
   */
  @Scheduled(cron = "0 0 3 15 * ? *")
  public void crawlerLastMonth() {
  }

  /**
   * TODO 分析前一天所有类别的视频播放情况
   */
  @Scheduled(cron = "0 0 4 * * ? ")
  public void analysisYesterdayCtgData() {

  }

  /**
   * TODO 分析上个月所有类别的视频播放情况
   */
  @Scheduled(cron = "0 0 4 15 * ? *")
  public void analysisLastMonthCtgData() {

  }

  /**
   * TODO 分析上个月各类别的tag分析
   */
  @Scheduled(cron = "0 0 4 15 * ? *")
  public void analysisLastMonthTagCount() {

  }


}