package growth.bach.instance.engine.task.entity;

import growth.bach.instance.dto.TaskInstanceDTO;

/**
 * 任务实例感知接口
 * @author brain
 * @version 1.0
 * @date 2024/11/1 14:16
 */
public interface TaskInstanceAware {
    void setTaskInstance(TaskInstanceDTO taskInstance);

    TaskInstanceDTO getTaskInstance();
}
