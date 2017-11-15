package github.eddy.bigdata.core.hadoop;

import com.mongodb.hadoop.MongoInputFormat;
import com.mongodb.hadoop.MongoOutputFormat;
import github.eddy.bigdata.core.common.JobEnum;
import github.eddy.common.HadoopTools;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

import static org.apache.hadoop.mapreduce.Job.getInstance;

/**
 * Hadoop Job 构造器
 *
 * @author edliao
 */
@Slf4j
public class HadoopJobBuilder {
    /**
     * hadoop任务
     */
    private Job job;
    /**
     * 当前任务的配置
     */
    private Configuration conf;

    HadoopJobBuilder config(Configuration conf, JobEnum jobEnum) throws IOException {
        this.conf = conf;
        this.job = getInstance(conf, jobEnum.name());
        job.setJarByClass(this.getClass());
        switch (jobEnum) {
            case mongodb:
                return initMongo();
            default:
                return this;
        }
    }

    /**
     * 如果是mongodb 自动设置序列化模式
     */
    private HadoopJobBuilder initMongo() {
        job.setInputFormatClass(MongoInputFormat.class);
        job.setOutputFormatClass(MongoOutputFormat.class);
        return this;
    }

    public HadoopJobBuilder addMapper(Class<? extends Mapper> mapperClass) throws IOException {
        HadoopTools.addMapper(job, mapperClass, conf);
        return this;
    }


    public HadoopJobBuilder setReducer(Class<? extends Reducer> reducerClass) {
        HadoopTools.setReducer(job, reducerClass, conf);
        return this;
    }

    public HadoopJobBuilder afterReducer(Class<? extends Mapper> mapperClass) throws IOException {
        HadoopTools.afterReducer(job, mapperClass, conf);
        return this;
    }

    Job build() {
        return job;
    }
}
