package github.eddy.bigdata.bilibili.task.analysis;

import static github.eddy.bigdata.core.common.TableEnum.analysis;
import static github.eddy.bigdata.core.common.TableEnum.source;
import static github.eddy.bigdata.core.configuration.MongoManager.mongoConfig;
import static github.eddy.common.DateTools.getYYYYMM;

import github.eddy.bigdata.core.beans.KnownException;
import github.eddy.bigdata.core.hadoop.HadoopJobBuilder;
import github.eddy.bigdata.core.hadoop.mapreduce.TextSumReducer;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.bson.BSONObject;

@Slf4j
public class TagSplit implements IAnalysis {

  String in;
  String out;

  public TagSplit(Integer year, Integer month) {
    String yyyyMM = getYYYYMM(year, month);
    in = source.getTableName("search", yyyyMM);
    out = analysis.getTableName("tagcount", yyyyMM);
  }

  @Override
  public Job submit() {
    try {
      return new HadoopJobBuilder()
          .config(mongoConfig(in, out))
          .initMongo()
          .addMapper(SpliterMapper.class)
          .setReducer(TextSumReducer.class)
          .build();
    } catch (IOException e) {
      log.error("", e);
      throw new KnownException("任务创建失败:", e);
    }
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