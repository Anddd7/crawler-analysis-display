package github.eddy.bigdata.bilibili.crawler.api;

import github.eddy.bigdata.bilibili.crawler.api.messages.SearchRequest;
import github.eddy.common.DateTools;
import java.util.Map;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiSearch {

  public void monitorMonthData(Integer year, Integer month, Integer cateId,
      Consumer<Map> consumer) {
    String[] dateRange = DateTools.getDateRangeYYYYMMDD(year, month);
    monitorData(cateId, dateRange[0], dateRange[1], consumer);
  }

  public void monitorData(Integer cateId, String timeFrom, String timeTo,
      Consumer<Map> consumer) {
    SearchRequest request = new SearchRequest(cateId, timeFrom, timeTo);
    while (request.hasNext()) {
      try {
        request.next().getResult().forEach(consumer);
      } catch (Exception e) {
        log.error("", e);
      }
    }
  }
}
