package growth.bach.instance.engine.task;

import growth.bach.instance.dto.TaskInstanceDTO;
import growth.bach.instance.dto.TaskMetaDTO;
import growth.bach.instance.engine.event.EventEngine;
import growth.bach.instance.engine.event.reg.DefaultTriggerInfo;
import growth.bach.instance.engine.event.reg.TriggerCondition;
import growth.bach.instance.engine.task.entity.TaskEntity;
import growth.bach.instance.engine.task.factory.TaskEntityFactory;
import growth.bach.instance.entity.TaskInstanceDO;
import growth.bach.instance.entity.TaskMetaDO;
import growth.bach.instance.enums.TaskInstanceStatus;
import growth.bach.instance.enums.TaskStatus;
import growth.bach.instance.repository.TaskInstanceRepository;
import growth.bach.instance.repository.TaskMetaRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/31 22:49
 */
public class TaskEngine {
    private List<TaskEntityFactory<? extends TaskEntity>> entityFactories;
    private TaskMetaRepository taskMetaRepository;
    private TaskInstanceRepository taskInstanceRepository;

    private EventEngine eventEngine;
    public TaskEntity createTask(Long taskMetaId, String userId, String activityId) {
        if (StringUtils.isBlank(userId)){
            // 异常
        }

        TaskMetaDO taskMetaDO = taskMetaRepository.getTaskMeta(taskMetaId, activityId);
        if (Objects.isNull(taskMetaDO)){
            // log 异常
        }
        TaskMetaDTO taskMetaDTO = TaskMetaDTO.from(taskMetaDO);
        if (!checkCreateCondition(taskMetaDTO, userId)){
            // 日志 异常
        }
        TaskInstanceDTO instance = getUnfinishedUnexpiredInstance(taskMetaDO, userId);
        boolean isNew = instance == null;
        TaskEntity taskEntity = isNew ? buildTaskEntity(TaskInstanceDTO.of(taskInstanceRepository.create(taskMetaDO, userId), taskMetaDO)) : buildTaskEntity(instance);
        afterTaskEntityCreated(taskEntity, isNew);
        return taskEntity;
    }

    private void afterTaskEntityCreated(TaskEntity taskEntity, boolean isNew) {
        taskEntity.afterEntityCreated();
        if (isNew){
            // Cat监控

            //注册触发事件
            registerTriggerCondition(taskEntity);
        }
    }


    private TaskEntity buildTaskEntity(TaskInstanceDTO taskInstanceDTO) {
        TaskEntity taskEntity = entityFactories.stream().filter(it -> it.support(taskInstanceDTO)).findFirst()
                .map(it -> it.get(taskInstanceDTO))
                .orElse(null);
        if (taskEntity == null){
            // 日志 异常
        }
        return taskEntity;
    }

    private TaskInstanceDTO getUnfinishedUnexpiredInstance(TaskMetaDO taskMetaDO, String userId) {
        List<TaskInstanceDO> dos = taskInstanceRepository.queryByTaskMetaId(userId, taskMetaDO.getId(), taskMetaDO.getActivityId());
        List<TaskInstanceDO> candidates = dos.stream()
                .filter(it -> TaskInstanceStatus.isOngoing(TaskInstanceStatus.from(it.getTaskInstanceStatus())))
                .filter(it -> it.getExpireTime().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(candidates)){
            return TaskInstanceDTO.from(candidates.get(0));
        }
        return null;
    }

    /**
     * 条件校验
     * @param taskMetaDTO
     * @param userId
     * @return
     */
    private boolean checkCreateCondition(TaskMetaDTO taskMetaDTO, String userId) {
        if (!taskMetaDTO.getTaskCreateCondition().satisfy(userId)){
            // 日志
            return false;
        }
        if (!TaskStatus.ONLINE.equals(taskMetaDTO.getTaskStatus())){
            // 日志
            return false;
        }
        return true;
    }


    private void registerTriggerCondition(TaskEntity taskEntity) {
        TriggerCondition triggerCondition = taskEntity.getTriggerCondition();
        if (triggerCondition == null){
            return;
        }
        TaskInstanceDTO instance = taskEntity.getTaskInstance();
        triggerCondition.setUserId(instance.getUserId());
        LocalDateTime expireTime = Optional.ofNullable(triggerCondition.getExpireTime()).orElse(instance.getTaskMeta().getActivityExpireTime());
        eventEngine.register(triggerCondition, new DefaultTriggerInfo(instance.getId()), expireTime);
    }

    public TaskEntity getTaskById(Long taskInstanceId, String userId) {
        TaskInstanceDO taskInstanceDO = taskInstanceRepository.queryById(taskInstanceId, userId);
        return buildTaskEntity(TaskInstanceDTO.from(taskInstanceDO));
    }
}
