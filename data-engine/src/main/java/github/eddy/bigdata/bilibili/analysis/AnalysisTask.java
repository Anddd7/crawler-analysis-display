package github.eddy.bigdata.bilibili.analysis;

import static github.eddy.bigdata.bilibili.common.TableEnum.analysis;
import static github.eddy.bigdata.bilibili.common.TableEnum.source;
import static github.eddy.bigdata.bilibili.configuration.MongoManager.mongoConfig;
import static github.eddy.common.DateTools.getYYYYMM;

import github.eddy.bigdata.bilibili.analysis.mapreduce.TagSplitMapper;
import github.eddy.bigdata.bilibili.analysis.mapreduce.TextSumReducer;
import github.eddy.bigdata.hadoop.HadoopMongoJob;
import java.io.IOException;
import lombok.experimental.UtilityClass;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.bson.BSONObject;

@UtilityClass
public class AnalysisTask {

  public static void tagCount(int year, int month)
      throws IOException, ClassNotFoundException, InterruptedException {
    String yyyyMM = getYYYYMM(year, month);
    new HadoopMongoJob()
        .init(mongoConfig(
            source.getTableName("search", yyyyMM),
            analysis.getTableName("tagcount", yyyyMM)))
        .addMapper(TagSplitMapper.class,
            Object.class, BSONObject.class, Text.class, IntWritable.class)
        .setReducer(TextSumReducer.class,
            Text.class, IntWritable.class, Text.class, IntWritable.class)
        .waitForCompletion();
  }

}
