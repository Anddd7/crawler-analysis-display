package github.eddy.bigdata.core.hadoop.mapreduce;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * @author edliao
 * 计数
 */
@Slf4j
public class TextSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

  private static final IntWritable result = new IntWritable();

  @Override
  public void reduce(Text key, Iterable<IntWritable> values, Context context)
      throws IOException, InterruptedException {
    int sum = 0;
    for (IntWritable val : values) {
      sum += val.get();
    }
    result.set(sum);
    context.write(key, result);
  }
}
