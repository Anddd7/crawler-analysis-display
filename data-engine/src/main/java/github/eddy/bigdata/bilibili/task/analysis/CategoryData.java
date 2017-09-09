package github.eddy.bigdata.bilibili.task.analysis;

import com.mongodb.hadoop.io.BSONWritable;
import github.eddy.bigdata.core.hadoop.HadoopJobBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;

import java.io.IOException;

import static github.eddy.bigdata.core.common.TableEnum.analysis;
import static github.eddy.bigdata.core.common.TableEnum.source;
import static github.eddy.common.DateTools.getYYYYMM;

@Slf4j
public class CategoryData extends AbstractMongoAnalysis {

    public CategoryData(Integer year, Integer month) {
        String yyyyMM = getYYYYMM(year, month);
        in = source.table("search", yyyyMM);
        out = analysis.table("categorydata", yyyyMM);
    }

    @Override
    public void configMapperReducer(HadoopJobBuilder builder) throws IOException {
        builder.addMapper(CategoryMapper.class)
               .setReducer(CategoryReducer.class);
    }

    public static class CategoryMapper extends Mapper<Object, BSONObject, IntWritable, ArrayWritable> {

        private static final IntWritable cateId = new IntWritable();
        private static final ArrayWritable result = new ArrayWritable(LongWritable.class);

        @Override
        protected void map(Object key, BSONObject value, Context context) throws IOException, InterruptedException {
            Long play = Long.valueOf((String) value.get("play"));
            Long favorites = (Long) value.get("favorites");
            Long review = (Long) value.get("review");
            Long videoReview = (Long) value.get("video_review");

            cateId.set((Integer) value.get("cateid"));
            result.set(new Writable[]{new LongWritable(play), new LongWritable(favorites), new LongWritable(
                    review), new LongWritable(videoReview)});
            context.write(cateId, result);
        }
    }

    public static class CategoryReducer extends Reducer<IntWritable, ArrayWritable, IntWritable, BSONWritable> {

        private static final BSONWritable bsonWritableon = new BSONWritable();

        @Override
        protected void reduce(IntWritable key, Iterable<ArrayWritable> values,
                              Context context) throws IOException, InterruptedException {
            for (ArrayWritable value : values) {
                LongWritable[] writables = (LongWritable[]) value.get();

                BSONObject bson = new BasicBSONObject();
                bson.put("play", writables[0].get());
                bson.put("favorites", writables[1].get());
                bson.put("review", writables[2].get());
                bson.put("videoReview", writables[3].get());
                bsonWritableon.setDoc(bson);
                context.write(key, bsonWritableon);
            }
        }
    }
}
