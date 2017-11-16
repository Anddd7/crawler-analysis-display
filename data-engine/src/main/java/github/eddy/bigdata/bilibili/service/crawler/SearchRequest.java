package github.eddy.bigdata.bilibili.service.crawler;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import github.eddy.bigdata.core.beans.KnownException;
import github.eddy.common.HttpTools;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author edliao
 * bilibili视频搜索API的封装
 */
@Slf4j
public class SearchRequest {
    enum ORDER {
        //点击
        click,
        //收藏
        stow,
        //评分
        scores,
        //弹幕
        dm,
        //硬币
        coin
    }

    enum COPYRIGHT {
        // 全部
        ALL(-1),
        // 原创
        OWN(1);

        int code;

        COPYRIGHT(int code) {
            this.code = code;
        }
    }

    /**
     * 默认参数
     */
    private static final String API_URL = "https://s.search.bilibili.com/cate/search";
    private static final String MAIN_VER = "v3";
    private static final String SEARCH_TYPE = "video";
    private static final String VIEW_TYPE = "hot_rank";
    private static final String PIC_SIZE = "160x100";

    private String order = ORDER.click.name();
    private Integer copyRight = COPYRIGHT.ALL.code;

    private Integer page = 0;
    private Integer pagesize = 100;

    /**
     * 必填参数
     */
    private final Integer cateId;
    private final String timeFrom;
    private final String timeTo;

    /**
     * 标识是否还有下一页
     */
    @Getter
    private Boolean hasNext = true;
    /**
     * 完整的调用url
     */
    @Getter
    private String longURL;
    private final String urlCache;


    /**
     * 构造查询某一时间段视频的Request
     *
     * @param cateId
     * @param timeFrom
     * @param timeTo
     */
    public SearchRequest(Integer cateId, String timeFrom, String timeTo) {
        this.cateId = cateId;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        //拼接基本的URL ,后续只需要加上分页page坐标即可
        this.urlCache = Joiner.on("&")
                .withKeyValueSeparator("=")
                .join(ImmutableMap.builder()
                        .put("main_ver", MAIN_VER)
                        .put("search_type", SEARCH_TYPE)
                        .put("view_type", VIEW_TYPE)
                        .put("pic_size", PIC_SIZE)
                        .put("order", order)
                        .put("copy_right", copyRight)
                        .put("cate_id", cateId)
                        .put("pagesize", pagesize)
                        .put("time_from", timeFrom)
                        .put("time_to", timeTo)
                        .build());
    }

    public SearchResponse next() {
        SearchResponse res = get(this.page + 1);
        if (res.numPages == 0 || res.numPages.equals(page)) {
            hasNext = false;
        }
        return res;
    }

    public SearchResponse get(Integer page) {
        if (page < 1) {
            throw new KnownException("page must > 0");
        }
        this.page = page;
        return response(request());
    }

    /**
     * 请求目标url
     *
     * @throws IOException 链接异常
     */
    private String request() {
        longURL = API_URL + "?" + urlCache + "&page=" + page;
        try {
            return HttpTools.getInstance().get(longURL);
        } catch (IOException e) {
            log.error("", e);
            throw new KnownException("IO连接出错", e);
        }
    }

    /**
     * 返回序列化的Response类
     *
     * @param json
     */
    private SearchResponse response(String json) {
        SearchResponse searchResponse = JSON.parseObject(json, SearchResponse.class);
        if (searchResponse.code != 0) {
            throw new KnownException("返回Code错误:" + searchResponse.code);
        }
        searchResponse.setJson(json);
        searchResponse.setCateid(cateId);
        return searchResponse;
    }
}
