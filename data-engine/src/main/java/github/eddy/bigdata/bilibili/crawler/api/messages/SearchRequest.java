package github.eddy.bigdata.bilibili.crawler.api.messages;

public class SearchRequest {

  public static final String ORDER_CLICK = "click";// 点击
  public static final String ORDER_STOW = "stow";// 收藏
  public static final String ORDER_SCORES = "scores";// 评分
  public static final String ORDER_DM = "dm";// 弹幕
  public static final String ORDER_COIN = "coin";// 硬币

  public static final Integer COPYRIGHT_ALL = -1;// 全部
  public static final Integer COPYRIGHT_OWN = 1;// 原创


  String main_ver = "v3";
  String search_type = "video";
  String view_type = "hot_rank";
  String pic_size = "160x100";
  String order = ORDER_CLICK;
  Integer copy_right = COPYRIGHT_ALL;
  String cate_id;
  Integer page = 0;
  Integer pagesize = 100;
  String time_from;
  String time_to;
}
