package growth.bach.instance.engine.task.factory;

import growth.bach.common.lang.spi.IdentityScanner;
import growth.bach.instance.dto.TaskInstanceDTO;
import growth.bach.instance.engine.task.entity.TaskEntity;
import lombok.SneakyThrows;

import java.util.Map;

/**
 * 默认任务实体工厂
 * 支持扫描{@link  growth.bach.common.lang.spi.Identity}
 * @author brain
 * @version 1.0
 * @date 2024/11/2 9:59
 */
public class DefaultTaskEntityFactory implements TaskEntityFactory<TaskEntity>{
    private final Map<String, Class<? extends TaskEntity>> idClassMap;
    {
        IdentityScanner<TaskEntity> scanner = new IdentityScanner<>();
        idClassMap = scanner.scan(TaskEntity.class, "com.xiaohongshu.growth.bach");
    }
    @Override
    public boolean support(TaskInstanceDTO instance) {
        return idClassMap.containsKey(instance.getTaskMeta().getTaskType().name());
    }

    @SneakyThrows
    @Override
    public TaskEntity get(TaskInstanceDTO instance) {
        TaskEntity taskEntity = idClassMap.get(instance.getTaskMeta().getTaskType().name()).getDeclaredConstructor().newInstance();
        taskEntity.setTaskInstance(instance);
        return taskEntity;
    }
}
