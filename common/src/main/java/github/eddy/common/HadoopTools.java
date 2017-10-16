package github.eddy.common;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import lombok.experimental.UtilityClass;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.chain.ChainMapper;
import org.apache.hadoop.mapreduce.lib.chain.ChainReducer;

@UtilityClass
public class HadoopTools {

  public static void addMapper(Job job, Class<? extends Mapper> mapperClass,
      Configuration conf) throws IOException {
    ParameterizedType type = (ParameterizedType) mapperClass.getGenericSuperclass();
    Class<?> inputKeyClass = (Class) type.getActualTypeArguments()[0];
    Class<?> inputValueClass = (Class) type.getActualTypeArguments()[1];
    Class<?> outputKeyClass = (Class) type.getActualTypeArguments()[2];
    Class<?> outputValueClass = (Class) type.getActualTypeArguments()[3];
    ChainMapper.addMapper(job, mapperClass, inputKeyClass, inputValueClass,
        outputKeyClass, outputValueClass, conf);
  }

  public static void setReducer(Job job, Class<? extends Reducer> reducerClass,
      Configuration conf) {
    ParameterizedType type = (ParameterizedType) reducerClass.getGenericSuperclass();
    Class<?> inputKeyClass = (Class) type.getActualTypeArguments()[0];
    Class<?> inputValueClass = (Class) type.getActualTypeArguments()[1];
    Class<?> outputKeyClass = (Class) type.getActualTypeArguments()[2];
    Class<?> outputValueClass = (Class) type.getActualTypeArguments()[3];
    ChainReducer.setReducer(job, reducerClass, inputKeyClass, inputValueClass,
        outputKeyClass, outputValueClass, conf);
  }

  public static void afterReducer(Job job, Class<? extends Mapper> mapperClass, Configuration conf)
      throws IOException {
    ParameterizedType type = (ParameterizedType) mapperClass.getGenericSuperclass();
    Class<?> inputKeyClass = (Class) type.getActualTypeArguments()[0];
    Class<?> inputValueClass = (Class) type.getActualTypeArguments()[1];
    Class<?> outputKeyClass = (Class) type.getActualTypeArguments()[2];
    Class<?> outputValueClass = (Class) type.getActualTypeArguments()[3];
    ChainReducer.addMapper(job, mapperClass, inputKeyClass, inputValueClass,
        outputKeyClass, outputValueClass, conf);
  }
}
