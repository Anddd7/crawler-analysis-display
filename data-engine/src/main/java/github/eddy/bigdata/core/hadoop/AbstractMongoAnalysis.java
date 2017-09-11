package github.eddy.bigdata.core.hadoop;

import com.google.common.base.Preconditions;
import github.eddy.bigdata.core.beans.KnownException;
import github.eddy.bigdata.core.common.JobEnum;
import github.eddy.bigdata.core.dao.MongodbDao;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static github.eddy.bigdata.core.configuration.MongoManager.mongoConfig;

@Slf4j
public abstract class AbstractMongoAnalysis {

    abstract public void configMapperReducer(HadoopJobBuilder builder) throws IOException;

    public void execute(String in, String out) {
        Preconditions.checkNotNull(in, out);
        MongodbDao.drop(out);
        try {
            HadoopJobBuilder builder = new HadoopJobBuilder();
            builder.config(mongoConfig(in, out), JobEnum.mongodb);
            configMapperReducer(builder);
            builder.build()
                    .waitForCompletion(true);
        } catch (IOException e) {
            log.error("", e);
            throw new KnownException("任务创建失败:", e);
        } catch (Exception e) {
            log.error("", e);
            throw new KnownException("任务执行失败:", e);
        }
    }
}
