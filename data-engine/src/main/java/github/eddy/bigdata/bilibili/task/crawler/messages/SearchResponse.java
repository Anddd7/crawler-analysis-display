package github.eddy.bigdata.bilibili.task.crawler.messages;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

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
