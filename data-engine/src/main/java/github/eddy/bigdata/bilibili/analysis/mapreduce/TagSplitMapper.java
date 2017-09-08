package github.eddy.bigdata.bilibili.analysis.mapreduce;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.bson.BSONObject;

@Slf4j
public class TagSplitMapper extends Mapper<Object, BSONObject, Text, IntWritable> {

  private static final IntWritable one = new IntWritable(1);
  private Text word = new Text();

  public void map(Object key, BSONObject value, Context context)
      throws IOException, InterruptedException {
    String tags = (String) value.get("tag");
    for (String s : tags.split(",")) {
      word.set(s);
      context.write(word, one);
    }
  }
}
