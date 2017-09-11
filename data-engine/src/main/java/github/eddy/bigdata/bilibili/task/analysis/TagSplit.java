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

@Slf4j
public class TagSplit extends AbstractMongoAnalysis {

    @Override
    public void configMapperReducer(HadoopJobBuilder builder) throws IOException {
        builder.addMapper(SpliterMapper.class)
                .setReducer(TextSumReducer.class);
    }

    public static class SpliterMapper extends Mapper<Object, BSONObject, Text, IntWritable> {

        private static final IntWritable one = new IntWritable(1);
        private Text word = new Text();

        @Override
        public void map(Object key, BSONObject value, Context context)
                throws IOException, InterruptedException {
            String tags = (String) value.get("tag");
            for (String s : tags.split(",")) {
                word.set(s);
                context.write(word, one);
            }
        }
    }
}