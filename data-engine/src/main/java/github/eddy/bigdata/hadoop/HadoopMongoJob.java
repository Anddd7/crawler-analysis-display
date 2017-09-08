package github.eddy.bigdata.hadoop;

import static org.apache.hadoop.mapreduce.Job.getInstance;

import com.mongodb.hadoop.MongoInputFormat;
import com.mongodb.hadoop.MongoOutputFormat;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.chain.ChainMapper;
import org.apache.hadoop.mapreduce.lib.chain.ChainReducer;

@Slf4j
public class HadoopMongoJob {

  private Job job;
  private Configuration conf;

  public HadoopMongoJob init(Configuration conf)
      throws IOException {
    this.conf = conf;
    job = getInstance(conf, "MongoAnalysis");
    job.setJarByClass(this.getClass());
    job.setInputFormatClass(MongoInputFormat.class);
    job.setOutputFormatClass(MongoOutputFormat.class);
    return this;
  }

  public HadoopMongoJob addMapper(Class<? extends Mapper> mapperClass, Class<?> inputKeyClass,
      Class<?> inputValueClass, Class<?> outputKeyClass, Class<?> outputValueClass)
      throws IOException {
    ChainMapper.addMapper(job, mapperClass, inputKeyClass, inputValueClass,
        outputKeyClass, outputValueClass, conf);
    return this;
  }


  public HadoopMongoJob setReducer(Class<? extends Reducer> reducerClass, Class<?> inputKeyClass,
      Class<?> inputValueClass, Class<?> outputKeyClass, Class<?> outputValueClass) {
    ChainReducer.setReducer(job, reducerClass, inputKeyClass, inputValueClass,
        outputKeyClass, outputValueClass, conf);
    return this;
  }

  public HadoopMongoJob afterReducer(Class<? extends Mapper> mapperClass, Class<?> inputKeyClass,
      Class<?> inputValueClass, Class<?> outputKeyClass, Class<?> outputValueClass)
      throws IOException {
    ChainReducer.addMapper(job, mapperClass, inputKeyClass, inputValueClass,
        outputKeyClass, outputValueClass, conf);
    return this;
  }

  public void waitForCompletion() throws InterruptedException, IOException, ClassNotFoundException {
    job.waitForCompletion(true);
  }
}
