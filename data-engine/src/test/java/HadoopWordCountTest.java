import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.junit.Test;

/**
 * word count , map/reduce的基础应用
 *
 * @单机模式 : hadoop单独启动 ,接受输入处理并输出
 * @(伪)分布式 : hadoop以平台模式启动 ,先上传输入到分布式文件系统hdfs ,执行任务时输入输出都是在分布式文件系统中进行操作
 */
public class HadoopWordCountTest {

  /**
   * Mapper ,以 key:value 格式进行处理 ,定义输入输出的格式
   */
  public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();

    @Override
    protected void map(Object key, Text value, Context context)
        throws IOException, InterruptedException {
      //对输入的文章进行分词
      StringTokenizer itr = new StringTokenizer(value.toString());
      while (itr.hasMoreTokens()) {
        word.set(itr.nextToken());
        context.write(word, one);//记录每一个 text:1 到context , 会形成 key:List[value] 的形式
      }
    }
  }

  /**
   * Reducer ,处理流程
   */
  public static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    private IntWritable result = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context)
        throws IOException, InterruptedException {
      int sum = 0;
      for (IntWritable val : values) {
        sum += val.get();
      }
      result.set(sum);
      context.write(key, result);
    }
  }

  @Test
  public void test() throws Exception {
    Configuration conf = new Configuration();

    Job job = Job.getInstance(conf, "word count");
    job.setJarByClass(HadoopWordCountTest.class);

    job.setMapperClass(TokenizerMapper.class);
    job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);

    //System.out.println(job.getInputFormatClass());//default TextInputFormat ,按行切分文件

    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);

    FileInputFormat.addInputPath(job, new Path("input/"));
    FileOutputFormat.setOutputPath(job, new Path("output/"));

    job.submit();
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
