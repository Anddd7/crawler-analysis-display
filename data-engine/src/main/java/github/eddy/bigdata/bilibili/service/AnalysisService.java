package github.eddy.bigdata.bilibili.service;

import static github.eddy.bigdata.bilibili.common.DBTableEnum.analysis_category_data;
import static github.eddy.bigdata.bilibili.common.DBTableEnum.analysis_tag_count;
import static github.eddy.bigdata.bilibili.common.DBTableEnum.source_search;

import github.eddy.bigdata.bilibili.service.analysis.CategoryData;
import github.eddy.bigdata.bilibili.service.analysis.TagCount;
import github.eddy.bigdata.bilibili.service.common.TaskParams;
import org.springframework.stereotype.Service;

/**
 * hadoop分析任务 ,主要进行大规模数据的抽取和分析 (无法通过mongodb sql实现的)
 *
 * @author edliao
 */
@Service
public class AnalysisService extends AbstractService {

  public void tagCount(TaskParams taskParams) {
    String yyyyMM = taskParams.getYYYYMM();
    new TagCount()
        .execute(dao.getDbName(),
            source_search.table(taskParams.getCateId().toString(), yyyyMM),
            analysis_tag_count.table(taskParams.getCateId().toString(), yyyyMM));
  }

  public void categoryData(TaskParams taskParams) {
    String yyyyMM = taskParams.getYYYYMM();
    new CategoryData()
        .execute(dao.getDbName(),
            source_search.table(taskParams.getCateId().toString(), yyyyMM),
            analysis_category_data.table(taskParams.getCateId().toString(), yyyyMM));
  }
}
