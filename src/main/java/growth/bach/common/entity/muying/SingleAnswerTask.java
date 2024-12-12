package growth.bach.common.entity.muying;

import growth.bach.common.entity.SimpleDeliverPrizeTask;
import growth.bach.common.holder.ApplicationContextHolder;
import growth.bach.common.lang.spi.Identity;
import growth.bach.instance.dto.TaskInstanceDTO;
import growth.bach.instance.engine.event.reg.TriggerCondition;
import growth.bach.instance.engine.progress.ProgressContext;
import growth.bach.instance.engine.task.entity.TaskEntity;
import growth.bach.instance.engine.task.muyingdati.condition.SingleAnswerPromoteCondition;
import growth.bach.instance.enums.TaskInstanceStatus;
import growth.bach.instance.repository.TaskInstanceRepository;
import growth.bach.manager.NotificationManager;
import growth.bach.manager.model.NotifyType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 单人答题任务
 * @author brain
 * @version 1.0
 * @date 2024/11/2 12:09
 */
@Slf4j
@Identity("MUYINGDATI_DANREN")
public class SingleAnswerTask implements TaskEntity, SimpleDeliverPrizeTask {
    private TaskInstanceDTO taskInstance;

    private final TaskInstanceRepository taskInstanceRepository = ApplicationContextHolder.getApplicationContext().getBean(TaskInstanceRepository.class);

    private final NotificationManager notificationManager = ApplicationContextHolder.getApplicationContext().getBean(NotificationManager.class);

    private final ChopinManager chopinManager = ApplicationContextHolder.getApplicationContext().getBean(ChopinManager.class);

    @Override
    public TriggerCondition getTriggerCondition() {
        return taskInstance.getTaskMeta().getTaskPromoteCondition().getTriggerCondition();
    }

    @Override
    public double goForward(ProgressContext context) {
        String userId = taskInstance.getUserId();

        SingleAnswerPromoteCondition promoteCondition = (SingleAnswerPromoteCondition) taskInstance.getTaskMeta().getTaskPromoteCondition();
        boolean incrSucceed = taskInstanceRepository.incrProgress(taskInstance.getId(), userId, promoteCondition.getStep());
        if (incrSucceed) {
            List<Pair<Long, Boolean>> deliverResult = deliverPrize(taskInstance, promoteCondition.getPrizeQuantity());
            if (deliverSucceed(deliverResult)) {
                Map<String, String> msg = new HashMap<>();
                msg.put("limit", String.valueOf(promoteCondition.getLimitShowing()));
                String activityId = taskInstance.getTaskMeta().getActivityId();
                msg.put("quantity", String.valueOf(taskInstance.getExtra().getIntValue("quantity") + promoteCondition.getPrizeQuantity()));
                notificationManager.notifyAsync(NotifyType.PRIZE_DELIVER, userId, activityId, msg);
            }
        }
        return 0;
    }

    private boolean deliverSucceed(List<Pair<Long, Boolean>> deliverResult) {
        if (CollectionUtils.isEmpty(deliverResult)) {
            return false;
        }
        return deliverResult.stream().allMatch(Pair::getRight);
    }

    @Override
    public boolean isFinished() {
        TaskInstanceStatus status = TaskInstanceStatus.from(taskInstanceRepository.queryById(taskInstance.getId(), taskInstance.getUserId()).getTaskInstanceStatus());
        return TaskInstanceStatus.FINISHED.equals(status);
    }

    /**
     * 任务结束不单独发奖
     *
     * @return
     */
    @Override
    public boolean deliverPrize() {
        return true;
    }

    @Override
    public void setTaskInstance(TaskInstanceDTO taskInstance) {
        this.taskInstance = taskInstance;
        Long taskMetaId = taskInstance.getTaskMeta().getId();
        String userId = taskInstance.getUserId();
        String activityId = taskInstance.getTaskMeta().getActivityId();
        this.taskInstance.getExtra().put("quantity", String.valueOf(chopinManager.getPointToday(taskMetaId, userId, activityId)));

        SingleAnswerPromoteCondition promoteCondition = (SingleAnswerPromoteCondition) taskInstance.getTaskMeta().getTaskPromoteCondition();
        this.taskInstance.getExtra().put("limit", promoteCondition.getLimitShowing());
    }

    @Override
    public TaskInstanceDTO getTaskInstance() {
        return taskInstance;
    }
}
