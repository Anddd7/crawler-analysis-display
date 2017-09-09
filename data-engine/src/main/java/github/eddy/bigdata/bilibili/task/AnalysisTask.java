package github.eddy.bigdata.bilibili.task;

import github.eddy.bigdata.bilibili.task.analysis.CategoryData;
import github.eddy.bigdata.bilibili.task.analysis.TagSplit;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * 数据分析
 */
@Slf4j
@UtilityClass
public class AnalysisTask {

    public static void tagCount(int year, int month) {
        new TagSplit(year, month).run();
    }

    public static void categoryData(int year, int month) {
        new CategoryData(year, month).run();
    }

}
