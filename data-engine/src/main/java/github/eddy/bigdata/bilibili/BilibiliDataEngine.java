package github.eddy.bigdata.bilibili;


import github.eddy.bigdata.bilibili.task.AbstractModule;
import github.eddy.bigdata.bilibili.task.AnalysisModule;
import github.eddy.bigdata.bilibili.task.CrawlerModule;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

/**
 * bilibili相关任务的启动和管理
 *
 * @author edliao
 * @implNote 把所有分析和抓取(需要耗时的)的操作都作为一个一个任务 ,每个任务的执行都有相应的记录来避免重复执行和查错redo
 */
@Slf4j
public class BilibiliDataEngine {

  Map<String, AbstractModule> moduleMap;

  public BilibiliDataEngine() {
    moduleMap = Stream
        .of(new CrawlerModule(), new AnalysisModule())
        .collect(Collectors.toMap(AbstractModule::getModuleName, Function.identity()));
  }

  /**
   * 执行任务
   *
   * @param module 模块
   * @param taskName 任务名
   * @param params 参数
   */
  private void execute(String module, String taskName, List<Object> params) {
    if (!moduleMap.containsKey(module)) {
      log.debug("指定的{}模块不存在", module);
      return;
    }
    moduleMap.get(module).execute(taskName, params);
  }


  public static void main(String[] a) {
    BilibiliDataEngine taskManager = new BilibiliDataEngine();

    taskManager.execute("crawler", "search", Arrays.asList(17, 2017, 6, null));
    taskManager.execute("crawler", "search", Arrays.asList(17, 2017, 7, null));
    taskManager.execute("crawler", "search", Arrays.asList(17, 2017, 8, null));
    taskManager.execute("crawler", "search", Arrays.asList(17, 2017, 9, null));

//        taskManager.execute("crawler", "search", Arrays.asList(2017, 8));
//        taskManager.execute("analysis", "tagsplit", Arrays.asList(2017, 7));
//        taskManager.execute("analysis", "tagsplit", Arrays.asList(2017, 8));
//        taskManager.execute("analysis", "categorydata", Arrays.asList(2017, 7));
//        taskManager.execute("analysis", "categorydata", Arrays.asList(2017, 8));
  }
}