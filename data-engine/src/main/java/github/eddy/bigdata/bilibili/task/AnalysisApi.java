package github.eddy.bigdata.bilibili.task;

import github.eddy.bigdata.bilibili.task.analysis.CategoryData;
import github.eddy.bigdata.bilibili.task.analysis.TagSplit;

import static github.eddy.bigdata.core.common.TableEnum.analysis;
import static github.eddy.bigdata.core.common.TableEnum.source;
import static github.eddy.common.DateTools.getYYYYMM;

public class AnalysisApi {
    public static void tagSplit(int year, int month) {
        String yyyyMM = getYYYYMM(year, month);
        String in = source.table("search", yyyyMM);
        String out = analysis.table("tagcount", yyyyMM);
        new TagSplit().execute(in, out);
    }

    public static void categoryData(int year, int month) {
        String yyyyMM = getYYYYMM(year, month);
        String in = source.table("search", yyyyMM);
        String out = analysis.table("categorydata", yyyyMM);
        new CategoryData().execute(in, out);
    }
}
