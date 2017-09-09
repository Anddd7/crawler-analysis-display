package github.eddy.bigdata.bilibili.task.crawler;

import github.eddy.bigdata.bilibili.task.crawler.messages.SearchRequest;
import github.eddy.common.DateTools;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Consumer;

@Slf4j
public class ApiSearch {

    public void monitorMonthData(Integer year, Integer month, Integer cateId, Consumer<List> consumer) {
        String[] dateRange = DateTools.getDateRangeYYYYMMDD(year, month);
        monitorData(cateId, dateRange[0], dateRange[1], consumer);
    }

    public void monitorData(Integer cateId, String timeFrom, String timeTo, Consumer<List> consumer) {
        SearchRequest request = new SearchRequest(cateId, timeFrom, timeTo);
        while (request.hasNext()) {
            try {
                consumer.accept(request.next()
                                       .getResult());
            } catch (Exception e) {
                log.error("获取数据失败:" + request.getLongURL(), e);
            }
        }
    }
}
