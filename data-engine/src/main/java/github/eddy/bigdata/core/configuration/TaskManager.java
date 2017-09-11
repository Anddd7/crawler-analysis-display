package github.eddy.bigdata.core.configuration;

import github.eddy.bigdata.core.task.ITask;
import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class TaskManager {
    Map<String, ITask> taskMap;


    public static void registerTask(ITask task) {

    }


}
