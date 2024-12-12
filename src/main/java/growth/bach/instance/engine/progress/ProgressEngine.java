package growth.bach.instance.engine.progress;

import com.google.common.collect.Maps;
import growth.bach.instance.dto.TaskInstanceDTO;
import growth.bach.instance.engine.event.UniqEvent;
import growth.bach.instance.engine.event.reg.TriggerInfo;
import growth.bach.instance.engine.task.TaskEngine;
import growth.bach.instance.engine.task.entity.TaskEntity;
import growth.bach.instance.enums.TaskInstanceStatus;
import growth.bach.manager.NotificationManager;
import growth.bach.manager.model.NotifyType;

import java.util.HashMap;

/**
 * 进度引擎
 * @author brain
 * @version 1.0
 * @date 2024/11/2 9:51
 */
public class ProgressEngine {
    private TaskEngine taskEngine;
    private NotificationManager notificationManager;
    /**
     * 通过事件推动任务进度
     * @param triggerInfo
     * @param uniqEvent
     */
    public void progressByEvent(TriggerInfo triggerInfo, UniqEvent uniqEvent) {
        TaskEntity task = taskEngine.getTaskById(triggerInfo.getTaskInstanceId(), uniqEvent.getUserId());
        TaskInstanceStatus status = task.getTaskInstance().getInstanceStatus();
        if (!TaskInstanceStatus.isOngoing(status)){
            // 日志
            return;
        }
        ProgressContext context = new ProgressContext();
        context.setEvent(uniqEvent);
        task.goForward(context);
        if (task.isFinished()){
            taskFinishAction(task);
        }
    }

    private void taskFinishAction(TaskEntity task) {
        // Cat打点 任务完成
        if (!task.deliverPrize()){
            // 打点 发奖状态信息
            //错误日志
            //异常 发奖错误
        }
        // 打点 发奖状态成功
        notify(task);
    }
    public void notify(TaskEntity task){
        TaskInstanceDTO taskInstance = task.getTaskInstance();
        HashMap<String, String> data = Maps.newHashMap();
        data.put("taskType", taskInstance.getTaskMeta().getTaskType().name());
        notificationManager.notifyAsync(NotifyType.TASK_COMPLETE, taskInstance.getUserId(), taskInstance.getTaskMeta().getActivityId(), data);
    }
}
