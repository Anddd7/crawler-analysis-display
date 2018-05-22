package com.github.anddd7.crawler.bilibili.repository;

import com.github.anddd7.crawler.bilibili.entity.Category;
import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepository {

  private final ImmutableMap<Integer, String> categories;

  public CategoryRepository() {
    this.categories = buildStaticCategories();
  }

  private ImmutableMap<Integer, String> buildStaticCategories() {
    return ImmutableMap.<Integer, String>builder()
        //    .put(0, "")
        //    .put(1, "全部")
        //    .put(3, "全部")
        //    .put(4, "全部")
        //    .put(5, "全部")
        //    .put(11, "全部")
        .put(15, "连载剧集")
        .put(17, "单机游戏")
        .put(19, "Mugen")
        .put(20, "宅舞")
        .put(21, "日常")
        .put(22, "鬼畜调教")
        .put(24, "MAD·AMV")
        .put(25, "MMD·3D")
        .put(26, "音MAD")
        .put(27, "综合")
        .put(28, "原创音乐")
        .put(29, "三次元音乐")
        .put(30, "VOCALOID·UTAU")
        .put(31, "翻唱")
        .put(32, "完结动画")
        .put(33, "连载动画")
        .put(34, "完结剧集")
        //    .put(36, "全部")
        .put(37, "纪录片")
        .put(39, "演讲•公开课")
        .put(47, "短片·手书·配音")
        .put(51, "资讯")
        .put(54, "OP/ED/OST")
        .put(59, "演奏")
        .put(65, "网络游戏")
        .put(71, "综艺")
        .put(75, "动物圈")
        .put(76, "美食圈")
        .put(82, "电影相关")
        .put(83, "其他国家")
        .put(85, "短片")
        .put(86, "特摄")
        .put(95, "数码")
        .put(96, "星海")
        .put(98, "机械")
        //    .put(119, "全部")
        .put(121, "GMV")
        .put(122, "野生技术协会")
        .put(124, "趣味科普人文")
        .put(126, "人力VOCALOID")
        .put(127, "教程演示")
        .put(128, "电视剧相关")
        //    .put(129, "全部")
        .put(130, "音乐选集")
        .put(131, "Korea相关")
        .put(136, "音游")
        .put(137, "明星")
        .put(138, "搞笑")
        .put(145, "欧美电影")
        .put(146, "日本电影")
        .put(147, "国产电影")
        .put(152, "官方延伸")
        .put(153, "国产动画")
        .put(154, "三次元舞蹈")
        .put(156, "舞蹈教程")
        .put(157, "美妆")
        .put(158, "服饰")
        .put(159, "资讯")
        //    .put(160, "全部")
        .put(161, "手工")
        .put(162, "绘画")
        .put(163, "运动")
        .put(164, "健身")
        .put(165, "广告")
        //    .put(166, "")
        //    .put(167, "全部")
        .put(168, "国产原创相关")
        .put(169, "布袋戏")
        .put(170, "资讯")
        .put(171, "电子竞技")
        .put(172, "手机游戏")
        .put(173, "桌游棋牌")
        .put(174, "其他")
        .build();
  }

  public List<Category> getCategories() {
    return categories.entrySet().stream()
        .map(entry -> new Category(entry.getKey(), entry.getValue()))
        .collect(Collectors.toList());
  }
}
