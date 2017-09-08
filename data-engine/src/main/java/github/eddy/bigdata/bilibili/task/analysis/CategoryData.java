package github.eddy.bigdata.bilibili.task.analysis;

import static github.eddy.bigdata.core.common.TableEnum.analysis;
import static github.eddy.bigdata.core.common.TableEnum.source;
import static github.eddy.bigdata.core.configuration.MongoManager.mongoConfig;
import static github.eddy.common.DateTools.getYYYYMM;

import github.eddy.bigdata.core.beans.KnownException;
import github.eddy.bigdata.core.hadoop.HadoopJobBuilder;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.bson.BSONObject;

@Slf4j
public class CategoryData implements IAnalysis {

  String in;
  String out;

  public CategoryData(Integer year, Integer month) {
    String yyyyMM = getYYYYMM(year, month);
    in = source.getTableName("search", yyyyMM);
    out = analysis.getTableName("categorydata", yyyyMM);
  }

  @Override
  public Job submit() {
    try {
      return new HadoopJobBuilder()
          .config(mongoConfig(in, out))
          .initMongo()
          .addMapper(CategoryMapper.class)
          .setReducer(CategoryReducer.class)
          .build();
    } catch (IOException e) {
      log.error("", e);
      throw new KnownException("任务创建失败:", e);
    }
  }


  public static class CategoryMapper extends Mapper<Object, BSONObject, IntWritable, Data> {

    private static final IntWritable cateId = new IntWritable();

    @Override
    protected void map(Object key, BSONObject value, Context context)
        throws IOException, InterruptedException {
      cateId.set((Integer) value.get("cateid"));
      Long play = Long.valueOf((String) value.get("play"));
      Long favorites = (Long) value.get("favorites");
      Long review = (Long) value.get("review");
      Long videoReview = (Long) value.get("video_review");
      context.write(cateId, new Data(play, favorites, review, videoReview));
    }
  }

  public static class CategoryReducer extends Reducer<IntWritable, Data, IntWritable, Map> {

    @Override
    protected void reduce(IntWritable key, Iterable<Data> values, Context context)
        throws IOException, InterruptedException {
      Data data = new Data();
      values.forEach(data::add);

      Map map = new HashMap();
      map.put("play", data.play);
      map.put("favorites", data.favorites);
      map.put("review", data.review);
      map.put("videoReview", data.videoReview);
      context.write(key,map );
    }
  }

  public static class Data {

    Long play = 0L;
    Long favorites = 0L;
    Long review = 0L;
    Long videoReview = 0L;

    public Data() {
    }

    public Data(Long play, Long favorites, Long review, Long videoReview) {
      this.play = play;
      this.favorites = favorites;
      this.review = review;
      this.videoReview = videoReview;
    }

    public void add(Data other) {
      this.play += other.play;
      this.favorites += other.favorites;
      this.review += other.review;
      this.videoReview += other.videoReview;
    }

    public Long getPlay() {
      return play;
    }

    public Long getFavorites() {
      return favorites;
    }

    public Long getReview() {
      return review;
    }

    public Long getVideoReview() {
      return videoReview;
    }
  }

}
