package github.eddy.bigdata.bilibili.task.analysis;

import org.apache.hadoop.mapreduce.Job;

public interface IAnalysis {

  Job submit();
}
