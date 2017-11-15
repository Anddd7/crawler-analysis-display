package github.eddy.bigdata.bilibili.task;

import static github.eddy.bigdata.bilibili.common.DBTableEnum.analysis_category_data;
import static github.eddy.bigdata.bilibili.common.DBTableEnum.analysis_tag_count;
import static github.eddy.bigdata.bilibili.common.DBTableEnum.source_search;
import static github.eddy.common.DateTools.getYYYYMM;

import github.eddy.bigdata.bilibili.task.analysis.CategoryData;
import github.eddy.bigdata.bilibili.task.analysis.TagCount;
import java.util.List;

/**
 * hadoop分析任务 ,主要进行大规模数据的抽取和分析 (无法通过mongodb sql实现的)
 *
 * @author edliao
 */
public class AnalysisModule extends AbstractModule {

  public void tagCount(int year, int month) {
    String yyyyMM = getYYYYMM(year, month);
    String in = source_search.table(yyyyMM);
    String out = analysis_tag_count.table(yyyyMM);
    new TagCount().execute("bilibili", in, out);
  }

  public void categoryData(int year, int month) {
    String yyyyMM = getYYYYMM(year, month);
    String in = source_search.table(yyyyMM);
    String out = analysis_category_data.table(yyyyMM);
    new CategoryData().execute("bilibili", in, out);
  }

  @Override
  void router(String taskName, List<Object> params) {

  }
}
