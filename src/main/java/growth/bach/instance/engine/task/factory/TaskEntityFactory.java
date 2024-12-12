package growth.bach.instance.engine.task.factory;

import growth.bach.instance.dto.TaskInstanceDTO;
import growth.bach.instance.engine.task.entity.TaskEntity;

/**
 * 任务实体工厂
 * 负责具体任务实体的创建工作，以及缓存实体等优化措施
 * @author brain
 * @version 1.0
 * @date 2024/11/1 14:15
 */
public interface TaskEntityFactory<T extends TaskEntity> {
    /**
     * 是否支持当前任务实例
     * @param instance 数据库中的任务实例
     * @return
     */
    boolean support(TaskInstanceDTO instance);

    /**
     * 获取任务实体
     * 可能创建新的，也可能是缓存的等
     * @param instance
     * @return
     */
    T get(TaskInstanceDTO instance);
}
