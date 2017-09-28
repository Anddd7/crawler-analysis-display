package github.eddy.bigdata.bilibili.task.crawler;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
public class SearchResponse {
    private String json;
    String exp_list;
    Integer numPages;
    Integer code;
    Integer pagesize;
    String suggest_keyword;
    Long numResults;
    String seid;
    String msg;
    Integer egg_hit;
    Integer page;
    List<Map> result;
}
