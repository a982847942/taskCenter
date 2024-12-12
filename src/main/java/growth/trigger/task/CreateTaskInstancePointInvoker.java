package growth.trigger.task;

import growth.trigger.QuizTaskPointConfig;
import growth.trigger.TaskPointInvoker;
import lombok.Data;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 21:52
 */
public class CreateTaskInstancePointInvoker implements TaskPointInvoker<CreateTaskInstancePointInvoker.Config, Void, TaskInstance> {

    @Override
    public String getPointName() {
        return "TAKE_TASK";
    }

    @Override
    public Class<Config> getConfigClazz() {
        return Config.class;
    }

    @Override
    public TaskInstance trigger(String activityId, String userId, QuizTaskPointConfig<Void> config, Config configParam, Void argument) {
        return bachServiceClient.createTask(activityId, userId, configParam.getTaskType(), configParam.getTaskMetaId());
    }

    @Data
    public static class Config {
        private Long taskMetaId;
        private String taskType;
    }
}
