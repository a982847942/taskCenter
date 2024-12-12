package growth.bach.common.entity;

import growth.bach.common.holder.ApplicationContextHolder;
import growth.bach.instance.TaskInstanceDomainService;
import growth.bach.instance.dto.TaskInstanceDTO;
import growth.bach.instance.engine.event.reg.TriggerCondition;
import growth.bach.instance.engine.progress.ProgressContext;
import growth.bach.instance.engine.task.entity.TaskEntity;
import growth.bach.instance.enums.TaskInstanceStatus;
import growth.bach.instance.req.UpdateInstanceReq;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * 一次触发就会完成的任务
 * @author brain
 * @version 1.0
 * @date 2024/11/2 10:05
 */
public abstract class BaseOneStepTask implements TaskEntity {
    @Getter
    private TaskInstanceDTO taskInstanceDTO;

    @Override
    public TriggerCondition getTriggerCondition() {
        return taskInstanceDTO.getTaskMeta().getTaskPromoteCondition().getTriggerCondition();
    }

    @Override
    public double goForward(ProgressContext context) {
        UpdateInstanceReq req = new UpdateInstanceReq();
        req.setInstanceId(taskInstanceDTO.getId());
        req.setUserId(taskInstanceDTO.getUserId());
        req.setTaskInstanceStatus(TaskInstanceStatus.FINISHED);
        Boolean res = ApplicationContextHolder.getApplicationContext().getBean(TaskInstanceDomainService.class).updateInstanceStatus(req);
        // 日志
        if (res){
            taskInstanceDTO.setInstanceStatus(TaskInstanceStatus.FINISHED);
            taskInstanceDTO.setProgress(new BigDecimal("100.0"));
        }
        actionAfterForward(context);
        return 100.0;
    }

    /**
     * 前进后的钩子
     * @param context
     */
    private void actionAfterForward(ProgressContext context) {

    }

    @Override
    public boolean isFinished() {
        TaskInstanceDTO dto = ApplicationContextHolder.getApplicationContext().getBean(TaskInstanceDomainService.class).queryInstanceId(taskInstanceDTO.getId(), taskInstanceDTO.getUserId());
        if (dto == null){
            return false;
        }
        return TaskInstanceStatus.FINISHED.equals(dto.getInstanceStatus());
    }

    @Override
    public void setTaskInstance(TaskInstanceDTO taskInstance) {
        this.taskInstanceDTO = taskInstance;
    }

    @Override
    public TaskInstanceDTO getTaskInstance() {
        return taskInstanceDTO;
    }
}
