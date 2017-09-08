package github.eddy.bigdata.bilibili.task;

import github.eddy.bigdata.bilibili.task.analysis.CategoryData;
import github.eddy.bigdata.bilibili.task.analysis.IAnalysis;
import github.eddy.bigdata.bilibili.task.analysis.TagSplit;
import github.eddy.bigdata.core.beans.KnownException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * 数据分析
 */
@Slf4j
@UtilityClass
public class AnalysisTask {

  public static void tagCount(int year, int month) {
    waitForCompletion(new TagSplit(year, month));
  }

  public static void categoryData(int year, int month) {
    waitForCompletion(new CategoryData(year, month));
  }

  private void waitForCompletion(IAnalysis analysis) {
    try {
      analysis.submit().waitForCompletion(true);
    } catch (Exception e) {
      log.error("", e);
      throw new KnownException("任务执行失败:", e);
    }
  }
}
