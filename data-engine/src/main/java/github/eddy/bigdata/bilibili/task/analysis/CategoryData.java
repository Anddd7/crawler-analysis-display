package github.eddy.bigdata.bilibili.task.analysis;

import com.mongodb.hadoop.io.BSONWritable;
import github.eddy.bigdata.core.hadoop.AbstractMongoAnalysis;
import github.eddy.bigdata.core.hadoop.HadoopJobBuilder;
import github.eddy.bigdata.core.hadoop.writable.LongArrayWritable;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;

import java.io.IOException;
import java.util.List;

/**
 * 计算各分区的数据
 */
@Slf4j
public class CategoryData extends AbstractMongoAnalysis {

    @Override
    public void configMapperReducer(HadoopJobBuilder builder) throws IOException {
        builder.addMapper(CategoryMapper.class)
                .setReducer(CategoryReducer.class);
    }

    public static class CategoryMapper extends Mapper<Object, BSONObject, IntWritable, LongArrayWritable> {

        private static final IntWritable cateId = new IntWritable();
        private static final LongArrayWritable result = new LongArrayWritable();

        @Override
        protected void map(Object key, BSONObject value, Context context)
                throws IOException, InterruptedException {
            String _play = value.get("play").toString();
            Long play = _play.equals("--") ? 0L : Long.valueOf(_play);
            Long favorites = Long.valueOf(value.get("favorites").toString());
            Long review = Long.valueOf(value.get("review").toString());
            Long videoReview = Long.valueOf(value.get("video_review").toString());

            cateId.set((Integer) value.get("cateid"));
            result.set(play, favorites, review, videoReview);
            context.write(cateId, result);
        }
    }

    public static class CategoryReducer extends Reducer<IntWritable, LongArrayWritable, IntWritable, BSONWritable> {

        private static final BSONWritable bsonWritableon = new BSONWritable();

        @Override
        protected void reduce(IntWritable key, Iterable<LongArrayWritable> values, Context context) throws IOException, InterruptedException {
            Long videoNum = 0L;
            Long play = 0L;
            Long favorites = 0L;
            Long review = 0L;
            Long videoReview = 0L;

            for (LongArrayWritable value : values) {
                List<LongWritable> writables = value.getLongWritable();
                videoNum++;
                play += writables.get(0).get();
                favorites += writables.get(1).get();
                review += writables.get(2).get();
                videoReview += writables.get(3).get();
            }

            BSONObject bson = new BasicBSONObject();
            bson.put("videoNum", videoNum);
            bson.put("play", play);
            bson.put("favorites", favorites);
            bson.put("review", review);
            bson.put("videoReview", videoReview);
            bsonWritableon.setDoc(bson);
            context.write(key, bsonWritableon);
        }
    }
}
