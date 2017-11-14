package github.eddy.bigdata.bilibili.task;

import github.eddy.bigdata.bilibili.task.analysis.CategoryData;
import github.eddy.bigdata.bilibili.task.analysis.TagSplit;
import github.eddy.bigdata.core.dao.BilibiliDao;

import static github.eddy.bigdata.bilibili.common.DBTableEnum.*;
import static github.eddy.common.DateTools.getYYYYMM;

public class BilibiliAnalysis {
    public void tagSplit(int year, int month) {
        String yyyyMM = getYYYYMM(year, month);
        String in = SOURCE_SEARCH.table(yyyyMM);
        String out = ANALYSIS_TAGCOUNT.table(yyyyMM);
        //删除之前的结果表
        BilibiliDao.drop(out);
        new TagSplit().execute("bilibili", in, out);
    }

    public void categoryData(int year, int month) {


        String yyyyMM = getYYYYMM(year, month);
        String in = SOURCE_SEARCH.table(yyyyMM);
        String out = ANALYSIS_CATEGORYDATA.table(yyyyMM);
        //删除之前的结果表
        BilibiliDao.drop(out);
        new CategoryData().execute("bilibili", in, out);
    }
}
