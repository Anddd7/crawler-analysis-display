package github.eddy.bigdata.bilibili.task.analysis;

import github.eddy.bigdata.core.hadoop.AbstractMongoAnalysis;
import github.eddy.bigdata.core.hadoop.HadoopJobBuilder;

import java.io.IOException;

/**
 * @author edliao
 * @since 2017/10/19
 *
 * 对视频描述的分析 ,使用中文分词包
 */
public class Description extends AbstractMongoAnalysis{

    @Override
    public void configMapperReducer(HadoopJobBuilder builder) throws IOException {

    }


}
