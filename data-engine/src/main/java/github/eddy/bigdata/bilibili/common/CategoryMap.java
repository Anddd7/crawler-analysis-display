package github.eddy.bigdata.bilibili.common;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 记录所有类别编号 ,忽略全部类别(投稿量少 但是有投稿 未知原因)
 *
 * @link py-script/bilibili/category.py
 * @apiNote 使用python脚本遍历0-200的编号 抓取投稿数>0的类别编号;同页面的标题栏tid比对 得出类别名称
 */
@UtilityClass
public class CategoryMap {

    private static final Map<Integer, String> categoryNames = new HashMap<>();

    static {
        //    categoryNames.put(0, "");
        //    categoryNames.put(1, "全部");//1
        //    categoryNames.put(3, "全部");//15
        //    categoryNames.put(4, "全部");//7
        //    categoryNames.put(5, "全部");//2
        //    categoryNames.put(11, "全部");//1
        categoryNames.put(15, "连载剧集");//46
        categoryNames.put(17, "单机游戏");//266408
        categoryNames.put(19, "Mugen");//4764
        categoryNames.put(20, "宅舞");//12518
        categoryNames.put(21, "日常");//114284
        categoryNames.put(22, "鬼畜调教");//8668
        categoryNames.put(24, "MAD·AMV");//18040
        categoryNames.put(25, "MMD·3D");//32560
        categoryNames.put(26, "音MAD");//3432
        categoryNames.put(27, "综合");//45632
        categoryNames.put(28, "原创音乐");//3081
        categoryNames.put(29, "三次元音乐");//55660
        categoryNames.put(30, "VOCALOID·UTAU");//11445
        categoryNames.put(31, "翻唱");//37934
        categoryNames.put(32, "完结动画");//1684
        categoryNames.put(33, "连载动画");//1131
        categoryNames.put(34, "完结剧集");//306
        //    categoryNames.put(36, "全部");//1
        categoryNames.put(37, "纪录片");//9266
        categoryNames.put(39, "演讲•公开课");//30854
        categoryNames.put(47, "短片·手书·配音");//14622
        categoryNames.put(51, "资讯");//1127
        categoryNames.put(54, "OP/ED/OST");//8096
        categoryNames.put(59, "演奏");//32188
        categoryNames.put(65, "网络游戏");//156761
        categoryNames.put(71, "综艺");//45069
        categoryNames.put(75, "动物圈");//38656
        categoryNames.put(76, "美食圈");//40872
        categoryNames.put(82, "电影相关");//44630
        categoryNames.put(83, "其他国家");//1
        categoryNames.put(85, "短片");//7973
        categoryNames.put(86, "特摄");//141
        categoryNames.put(95, "数码");//24735
        categoryNames.put(96, "星海");//7265
        categoryNames.put(98, "机械");//19601
        //    categoryNames.put(119, "全部");//1
        categoryNames.put(121, "GMV");//7964
        categoryNames.put(122, "野生技术协会");//21187
        categoryNames.put(124, "趣味科普人文");//24088
        categoryNames.put(126, "人力VOCALOID");//1584
        categoryNames.put(127, "教程演示");//111
        categoryNames.put(128, "电视剧相关");//96805
        //    categoryNames.put(129, "全部");//1
        categoryNames.put(130, "音乐选集");//45457
        categoryNames.put(131, "Korea相关");//56997
        categoryNames.put(136, "音游");//17639
        categoryNames.put(137, "明星");//100384
        categoryNames.put(138, "搞笑");//37177
        categoryNames.put(145, "欧美电影");//33
        categoryNames.put(146, "日本电影");//11
        categoryNames.put(147, "国产电影");//3099
        categoryNames.put(152, "官方延伸");//8460
        categoryNames.put(153, "国产动画");//1252
        categoryNames.put(154, "三次元舞蹈");//16066
        categoryNames.put(156, "舞蹈教程");//1008
        categoryNames.put(157, "美妆");//34864
        categoryNames.put(158, "服饰");//11267
        categoryNames.put(159, "资讯");//5861
        //    categoryNames.put(160, "全部");//14
        categoryNames.put(161, "手工");//19214
        categoryNames.put(162, "绘画");//17283
        categoryNames.put(163, "运动");//35763
        categoryNames.put(164, "健身");//5831
        categoryNames.put(165, "广告");//5
        //    categoryNames.put(166, "");
        //    categoryNames.put(167, "全部");//4
        categoryNames.put(168, "国产原创相关");//11648
        categoryNames.put(169, "布袋戏");//2623
        categoryNames.put(170, "资讯");//426
        categoryNames.put(171, "电子竞技");//131267
        categoryNames.put(172, "手机游戏");//97988
        categoryNames.put(173, "桌游棋牌");//14436
        categoryNames.put(174, "其他");//48520
    }

    public static String getCategoryName(Integer cateId) {
        return categoryNames.get(cateId);
    }

    public static Set<Integer> getCategoryIds() {
        return categoryNames.keySet();
    }
}
