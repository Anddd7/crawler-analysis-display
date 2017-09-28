package github.eddy.bigdata.bilibili.task.crawler;

import com.alibaba.fastjson.JSON;
import github.eddy.bigdata.core.beans.KnownException;
import github.eddy.common.DateTools;
import github.eddy.common.HttpTools;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class SearchRequest {
    public static final String API_URL = "https://s.search.bilibili.com/cate/search";

    public static final String ORDER_CLICK = "click";// 点击
    public static final String ORDER_STOW = "stow";// 收藏
    public static final String ORDER_SCORES = "scores";// 评分
    public static final String ORDER_DM = "dm";// 弹幕
    public static final String ORDER_COIN = "coin";// 硬币

    public static final Integer COPYRIGHT_ALL = -1;// 全部
    public static final Integer COPYRIGHT_OWN = 1;// 原创

    final String main_ver = "v3";
    final String search_type = "video";
    String view_type = "hot_rank";
    final String pic_size = "160x100";

    String order = ORDER_CLICK;
    Integer copy_right = COPYRIGHT_ALL;

    Integer page = 0;
    Integer pagesize = 100;

    Integer cate_id;
    String time_from;
    String time_to;

    private Boolean nextFlag = true;
    @Getter
    private String longURL;

  /*---------------------------------------------------------------------------------*/

    public SearchRequest(Integer cate_id, String time_from, String time_to) {
        this.cate_id = cate_id;
        this.time_from = time_from;
        this.time_to = time_to;
    }

    public SearchRequest(Integer cate_id, Integer year, Integer month) {
        String[] dateRange = DateTools.getDateRangeYYYYMMDD(year, month);
        this.cate_id = cate_id;
        this.time_from = dateRange[0];
        this.time_to = dateRange[1];
    }

  /*---------------------------------------------------------------------------------*/

    public Boolean hasNext() {
        return nextFlag;
    }

    public SearchResponse next() throws IOException {
        SearchResponse res = get(this.page + 1);
        if (res.numPages == 0 || res.numPages.equals(page)) {
            nextFlag = false;
        }
        return res;
    }

    public SearchResponse get(Integer page) throws IOException {
        if (page < 1) {
            throw new KnownException("page must > 0");
        }
        this.page = page;
        return response(get());
    }

    private String get() throws IOException {
        longURL = API_URL + "?" + getParam4URL();
        return HttpTools.getInstance().get(longURL);
    }

    private String getParam4URL() {
        return new StringBuilder()
                .append("main_ver").append("=").append(main_ver).append("&")
                .append("search_type").append("=").append(search_type).append("&")
                .append("view_type").append("=").append(view_type).append("&")
                .append("pic_size").append("=").append(pic_size).append("&")
                .append("order").append("=").append(order).append("&")
                .append("copy_right").append("=").append(copy_right).append("&")
                .append("cate_id").append("=").append(cate_id).append("&")
                .append("page").append("=").append(page).append("&")
                .append("pagesize").append("=").append(pagesize).append("&")
                .append("time_from").append("=").append(time_from).append("&")
                .append("time_to").append("=").append(time_to)
                .toString();
    }

    private SearchResponse response(String json) {
        SearchResponse searchResponse = JSON.parseObject(json, SearchResponse.class);
        if (searchResponse.code != 0) {
            throw new KnownException("返回Code错误:" + searchResponse.code);
        }
        searchResponse.setJson(json);
        searchResponse.getResult().forEach(map -> map.put("cateid", cate_id));
        return searchResponse;
    }
}
