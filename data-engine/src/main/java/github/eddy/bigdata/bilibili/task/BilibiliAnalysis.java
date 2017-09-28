package github.eddy.bigdata.bilibili.task;

import github.eddy.bigdata.bilibili.task.analysis.CategoryData;
import github.eddy.bigdata.bilibili.task.analysis.TagSplit;

import static github.eddy.bigdata.bilibili.common.DBTableEnum.*;
import static github.eddy.common.DateTools.getYYYYMM;

public class BilibiliAnalysis {
    public void tagSplit(int year, int month) {
        String yyyyMM = getYYYYMM(year, month);
        String in = source_search.table(yyyyMM);
        String out = analysis_tagcount.table(yyyyMM);
        new TagSplit().execute(in, out);
    }

    public void categoryData(int year, int month) {
        String yyyyMM = getYYYYMM(year, month);
        String in = source_search.table(yyyyMM);
        String out = analysis_categorydata.table(yyyyMM);
        new CategoryData().execute(in, out);
    }
}
