package github.eddy.bigdata.bilibili.crawler.api;

import github.eddy.bigdata.bilibili.crawler.api.messages.SearchRequest;
import github.eddy.bigdata.bilibili.model.SearchSourceSample;
import github.eddy.bigdata.core.CheckException;
import github.eddy.bigdata.utils.DateTools;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiSearch {

  public void monitorMonthData(Integer year, Integer month, Integer cateId,
      Consumer<SearchSourceSample> consumer) throws CheckException {
    String[] dateRange = DateTools.getDateRangeYYYYMMDD(year, month);
    monitorData(cateId, dateRange[0], dateRange[1], consumer);
  }

  public void monitorData(Integer cateId, String timeFrom, String timeTo,
      Consumer<SearchSourceSample> consumer) throws CheckException {
    SearchRequest request = new SearchRequest(cateId, timeFrom, timeTo);
    while (request.hasNext()) {
      request.next().getResult().forEach(consumer);
    }
  }
}
