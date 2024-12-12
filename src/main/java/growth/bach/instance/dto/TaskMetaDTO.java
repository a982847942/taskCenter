package growth.bach.instance.dto;

import growth.bach.instance.engine.parser.JsonParser;
import growth.bach.instance.engine.task.meta.BaseCreateCondition;
import growth.bach.instance.engine.task.meta.BaseEnterCondition;
import growth.bach.instance.engine.task.meta.BasePromoteCondition;
import growth.bach.instance.engine.task.meta.BaseSuccessCondition;
import growth.bach.instance.entity.TaskMetaDO;
import growth.bach.instance.enums.TaskStatus;
import growth.bach.instance.enums.TaskType;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/31 22:59
 */
@Data
public class TaskMetaDTO {
    private Long id;
    /**
     * 任务类型
     */
    private TaskType taskType;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 任务状态
     */
    private TaskStatus taskStatus;

    /**
     * 活动ID
     */
    private String activityId;

    private LocalDateTime activityExpireTime;
    private boolean saveSnapShot;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private BaseCreateCondition taskCreateCondition;
    private BaseEnterCondition taskEnterCondition;
    private BasePromoteCondition taskPromoteCondition;
    private BaseSuccessCondition taskSuccessCondition;

    public static TaskMetaDTO from(TaskMetaDO taskMetaDO){
        if (taskMetaDO == null){
            // 异常
        }
        TaskMetaDTO taskMetaDTO = new TaskMetaDTO();
        taskMetaDTO.setId(taskMetaDO.getId());
        taskMetaDTO.setTaskName(taskMetaDO.getTaskName());
        taskMetaDTO.setActivityId(taskMetaDO.getActivityId());
        taskMetaDTO.setTaskType(TaskType.from(taskMetaDO.getTaskType()));
        taskMetaDTO.setTaskStatus(TaskStatus.from(taskMetaDO.getTaskStatus()));
        taskMetaDTO.setSaveSnapShot(taskMetaDO.getSaveSnapshot());
        taskMetaDTO.setCreateTime(taskMetaDO.getCreateTime());
        taskMetaDTO.setUpdateTime(taskMetaDO.getUpdateTime());
        taskMetaDTO.setActivityExpireTime(taskMetaDO.getActivityExpireTime());

        if (taskMetaDO.getTaskCreateCondition() != null && StringUtils.isNotEmpty(taskMetaDO.getCreateConditionId())){
            taskMetaDTO.setTaskCreateCondition(JsonParser.CREATE_CONDITION.parse(taskMetaDO.getTaskCreateCondition(), taskMetaDO.getCreateConditionId()));
            taskMetaDTO.getTaskCreateCondition().setTaskMetaDTO(taskMetaDTO);
        }
        if (taskMetaDO.getTaskEnterCondition() != null && StringUtils.isNotEmpty(taskMetaDO.getEnterConditionId())){
            taskMetaDTO.setTaskEnterCondition(JsonParser.ENTER_CONDITION.parse(taskMetaDO.getTaskEnterCondition(), taskMetaDO.getEnterConditionId()));
        }

        if (taskMetaDO.getTaskPromoteCondition() != null && StringUtils.isNotEmpty(taskMetaDO.getPromoteConditionId())) {
            taskMetaDTO.setTaskPromoteCondition(JsonParser.PROMOTE_CONDITION.parse(taskMetaDO.getTaskPromoteCondition(), taskMetaDO.getPromoteConditionId()));
        }
        if (taskMetaDO.getTaskSuccessCondition() != null && StringUtils.isNotEmpty(taskMetaDO.getSuccessConditionId())){
            taskMetaDTO.setTaskSuccessCondition(JsonParser.SUCCESS_CONDITION.parse(taskMetaDO.getTaskSuccessCondition(), taskMetaDO.getSuccessConditionId()));
        }
        return taskMetaDTO;
    }

}
