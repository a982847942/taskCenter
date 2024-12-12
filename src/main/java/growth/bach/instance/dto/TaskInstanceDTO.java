package growth.bach.instance.dto;

import com.alibaba.fastjson.JSONObject;
import growth.bach.instance.entity.TaskInstanceDO;
import growth.bach.instance.entity.TaskMetaDO;
import growth.bach.instance.enums.TaskInstanceStatus;
import growth.bach.instance.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/31 22:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskInstanceDTO {
    private Long id;
    private String userId;
    //计数器
    private Double counts;
    //总进度
    private BigDecimal progress;
    //任务配置信息
    private TaskMetaDTO taskMeta;
    // 任务实例状态
    private TaskInstanceStatus instanceStatus;
    // 扩展字段
    private JSONObject extra;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public static TaskInstanceDTO from(TaskInstanceDO taskInstanceDO){
        if (Objects.isNull(taskInstanceDO)){
            return null;
        }

        // 数据库获取TaskMeta
        TaskMetaDO taskMetaDO = new TaskMetaDO();
        return TaskInstanceDTO.of(taskInstanceDO, taskMetaDO);
    }

    /**
     * 组装模型，如果配置了快照不使用taskMetaDO参数
     * @param instanceDO
     * @param taskMetaDO
     * @return
     */
    public static TaskInstanceDTO of(TaskInstanceDO instanceDO, TaskMetaDO taskMetaDO){
        return TaskInstanceDTO.of(instanceDO,TaskMetaDTO.from(taskMetaDO));
    }

    /**
     * 组装模型，如果配置了快照不使用taskMetaDO参数
     * @param instanceDO
     * @param taskMetaDTO
     * @return
     */
    public static TaskInstanceDTO of(TaskInstanceDO instanceDO, TaskMetaDTO taskMetaDTO){
        TaskInstanceDTO taskInstanceDTO = new TaskInstanceDTO();
        taskInstanceDTO.setId(instanceDO.getId());
        taskInstanceDTO.setUserId(instanceDO.getUserId());
        taskInstanceDTO.setCounts(instanceDO.getCounts());
        taskInstanceDTO.setProgress(instanceDO.getProgress());
        if (instanceDO.getSaveSnapshot()){
            TaskMetaDO taskMetaDO = instanceDO.getSnapShot().toJavaObject(TaskMetaDO.class);
            taskInstanceDTO.setTaskMeta(TaskMetaDTO.from(taskMetaDO));
        }else {
            taskInstanceDTO.setTaskMeta(taskMetaDTO);
        }
        taskInstanceDTO.setInstanceStatus(TaskInstanceStatus.from(instanceDO.getTaskInstanceStatus()));
        taskInstanceDTO.setCreateTime(instanceDO.getCreateTime());
        taskInstanceDTO.setUpdateTime(instanceDO.getUpdateTime());
        taskInstanceDTO.setExtra(instanceDO.getExtra());
        return taskInstanceDTO;
    }

    public static TaskInstanceDTO empty(String userId, TaskInstanceStatus status, TaskMetaDTO taskMeta){
        TaskInstanceDTO dto = new TaskInstanceDTO();
        dto.setUserId(userId);
        dto.setCounts(0D);
        dto.setProgress(new BigDecimal("0"));
        dto.setTaskMeta(taskMeta);
        dto.setInstanceStatus(status);
        dto.setCreateTime(LocalDateTime.now());
        dto.setUpdateTime(LocalDateTime.now());
        dto.setExtra(new JSONObject());
        return dto;
    }
}
