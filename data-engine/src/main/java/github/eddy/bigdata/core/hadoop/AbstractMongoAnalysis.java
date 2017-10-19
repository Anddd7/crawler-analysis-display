package github.eddy.bigdata.core.hadoop;

import com.google.common.base.Preconditions;
import github.eddy.bigdata.core.beans.KnownException;
import github.eddy.bigdata.core.common.JobEnum;
import github.eddy.bigdata.core.dao.MongodbDao;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static github.eddy.bigdata.core.configuration.MongoManager.mongoConfig;

/**
 * @author edliao
 */
@Slf4j
public abstract class AbstractMongoAnalysis {
    /**
     * 自动加载Job配置的方法
     *
     * @param builder Job配置
     * @throws IOException 添加mapper/reducer时可能抛出的异常
     */
    abstract public void configMapperReducer(HadoopJobBuilder builder) throws IOException;

    /**
     * 获取in/out集合 ,根据analysis的配置执行一系列操作
     */
    public void execute(String in, String out) {
        Preconditions.checkNotNull(in, out);
        //删除之前的结果表
        MongodbDao.drop(out);
        try {
            HadoopJobBuilder builder = new HadoopJobBuilder();
            //自动注入mongo+hadoop相关配置
            builder.config(mongoConfig(in, out), JobEnum.mongodb);
            //注入mapper/reducer
            configMapperReducer(builder);
            //运行
            builder.build().waitForCompletion(true);
        } catch (IOException e) {
            log.error("", e);
            throw new KnownException("任务创建失败:", e);
        } catch (Exception e) {
            log.error("", e);
            throw new KnownException("任务执行失败:", e);
        }
    }
}
