package github.eddy.common;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.chain.ChainMapper;
import org.apache.hadoop.mapreduce.lib.chain.ChainReducer;

/**
 * @author edliao 配置hadoop的一些工具类
 */
public class HadoopTools {

  /**
   * 向Job中添加mapper
   *
   * @param job 目标job
   * @param mapperClass 准备添加的mapper
   * @param conf hadoop配置
   */
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

  /**
   * 向Job添加reducer
   *
   * @param job 目job
   * @param reducerClass reducer
   * @param conf hadoop配置
   */
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

  /**
   * 添加after reducer
   *
   * @param job,mapperClass,conf {@link HadoopTools#setReducer(Job, Class, Configuration)}
   */
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
