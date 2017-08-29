package github.eddy.bigdata.bilibili.crawler.api.messages;

import github.eddy.bigdata.bilibili.model.SearchSourceSample;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
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
  List<SearchSourceSample> result;
}
