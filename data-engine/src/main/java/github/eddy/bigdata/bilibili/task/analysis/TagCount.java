package github.eddy.bigdata.bilibili.task.analysis;

import github.eddy.bigdata.core.hadoop.AbstractMongoAnalysis;
import github.eddy.bigdata.core.hadoop.HadoopJobBuilder;
import github.eddy.bigdata.core.hadoop.mapreduce.TextSumReducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.bson.BSONObject;

import java.io.IOException;

/**
 * 切分视频的tag并计算tag的出现频率
 *
 * @author edliao
 */
@Slf4j
public class TagCount extends AbstractMongoAnalysis {
    private static final String SPLITTER = ",";

    @Override
    public void configMapperReducer(HadoopJobBuilder builder) throws IOException {
        builder.addMapper(SplitterMapper.class)
                .setReducer(TextSumReducer.class);
    }

    public static class SplitterMapper extends Mapper<Object, BSONObject, Text, IntWritable> {
        private static final IntWritable ONE = new IntWritable(1);
        private Text word = new Text();

        @Override
        public void map(Object key, BSONObject value, Context context) throws IOException, InterruptedException {
            String tags = (String) value.get("tag");
            for (String s : tags.trim().split(SPLITTER)) {
                word.set(s.trim().toLowerCase());
                context.write(word, ONE);
            }
        }
    }
}