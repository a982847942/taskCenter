package growth.bach.common.entity.muying;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 12:19
 */

import com.alibaba.fastjson.JSONObject;
import com.sun.istack.internal.NotNull;
import growth.bach.common.entity.SimpleDeliverPrizeTask;
import growth.bach.common.entity.muying.notify.MultiAnswerNotifyManager;
import growth.bach.common.holder.ApplicationContextHolder;
import growth.bach.common.lang.spi.Identity;
import growth.bach.instance.dto.TaskInstanceDTO;
import growth.bach.instance.engine.event.UniqEvent;
import growth.bach.instance.engine.event.reg.TriggerCondition;
import growth.bach.instance.engine.progress.ProgressContext;
import growth.bach.instance.engine.task.entity.TaskEntity;
import growth.bach.instance.engine.task.muyingdati.condition.TeamAnswerSuccessCondition;
import growth.bach.instance.engine.task.muyingdati.event.TeamAnswerResult;
import growth.bach.instance.enums.TaskInstanceStatus;
import growth.bach.instance.repository.TaskInstanceRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组队答题任务
 * <p>
 * 每人一个任务，答完就推到完成
 * 结算奖励的时候限制平均分，达到标准的才发奖
 * 但是总会给前端一个奖励发放通知，如果两者都没中奖，就数量都是零
 *
 * @author yuzhun
 * @created 2024-07-08 22:52
 */
@Slf4j
@Identity("MUYINGDATI_ZUDUI")
public class TeamAnswerTask implements TaskEntity, SimpleDeliverPrizeTask {
    private TaskInstanceDTO taskInstance;

    private final TaskInstanceRepository taskInstanceRepository = ApplicationContextHolder.getApplicationContext().getBean(TaskInstanceRepository.class);

    private final MultiAnswerNotifyManager multiAnswerNotifyManager = ApplicationContextHolder.getApplicationContext().getBean(MultiAnswerNotifyManager.class);

    @Override
    public TriggerCondition getTriggerCondition() {
        return taskInstance.getTaskMeta().getTaskPromoteCondition().getTriggerCondition();
    }

    @Override
    public double goForward(ProgressContext context) {
        UniqEvent<TeamAnswerResult> event = context.getEvent();
        taskInstanceRepository.incrProgress(taskInstance.getId(), taskInstance.getUserId(), new BigDecimal("100.0"));
        storeEventContent(event);
        return 100D;
    }

    private void storeEventContent(UniqEvent<TeamAnswerResult> event) {
        JSONObject extra = new JSONObject();
        extra.put("teamId", event.getContent().getTeamId());
        extra.put("members", event.getContent().getMembers());
        extra.put("averageCorrectCounts", event.getContent().getAverageCorrectCounts());
        extra.put("correctCounts", event.getContent().getCorrectCounts());
        if (taskInstance.getExtra() == null) {
            taskInstance.setExtra(extra);
        } else {
            taskInstance.getExtra().putAll(extra);
        }
        taskInstanceRepository.updateTaskInstanceExtra(taskInstance.getId(), taskInstance.getUserId(), extra);
    }

    @Override
    public boolean isFinished() {
        TaskInstanceStatus status = TaskInstanceStatus.from(taskInstanceRepository.queryById(taskInstance.getId(), taskInstance.getUserId()).getTaskInstanceStatus());
        return TaskInstanceStatus.FINISHED.equals(status);
    }

    @Override
    public boolean deliverPrize() {
        TeamAnswerSuccessCondition successCondition = (TeamAnswerSuccessCondition) taskInstance.getTaskMeta().getTaskSuccessCondition();

        MultiAnswerNotifyManager.DeliverResult result = getDeliverResult(0);
        if (successCondition.getMinAverageSource() <= result.getAverageScore()) {
            List<Pair<Long, Boolean>> deliverResult = deliverPrize(taskInstance, successCondition.getQuantity());
            int deliveredQuantity = deliverSucceed(deliverResult) ? successCondition.getQuantity() : 0;
            result.setSatisfy(true);
            result.setQuantity(deliveredQuantity);
        }

        multiAnswerNotifyManager.deliverPrizeReport(result);
        return true;
    }

    private Map<String, Double> calcScores(Map<String, Integer> correctCounts) {
        TeamAnswerSuccessCondition successCondition = (TeamAnswerSuccessCondition) taskInstance.getTaskMeta().getTaskSuccessCondition();
        Map<String, Double> result = new HashMap<>();
        for (String k : correctCounts.keySet()) {
            result.put(k, correctCounts.get(k) * successCondition.getUnitScore());
        }
        return result;
    }

    private Double calcAverageScore(Double averageCorrectCounts) {
        TeamAnswerSuccessCondition successCondition = (TeamAnswerSuccessCondition) taskInstance.getTaskMeta().getTaskSuccessCondition();
        return averageCorrectCounts * successCondition.getUnitScore();
    }

    @NotNull
    private MultiAnswerNotifyManager.DeliverResult getDeliverResult(int deliveredQuantity) {
        MultiAnswerNotifyManager.DeliverResult result = new MultiAnswerNotifyManager.DeliverResult();
        result.setActivityId(taskInstance.getTaskMeta().getActivityId());
        result.setTeamId(taskInstance.getExtra().getString("teamId"));
        result.setUserId(taskInstance.getUserId());
        result.setQuantity(deliveredQuantity);
        result.setSatisfy(false);
        result.setTeamMembers(taskInstance.getExtra().getJSONArray("members").toJavaList(String.class));

        Map<String, Double> scores = calcScores(JacksonKits.toMap(taskInstance.getExtra().getJSONObject("correctCounts"), Integer.class, false));
        result.setScores(scores);

        Double averageScore = calcAverageScore(taskInstance.getExtra().getDouble("averageCorrectCounts"));
        result.setAverageScore(averageScore);
        return result;
    }

    private boolean deliverSucceed(List<Pair<Long, Boolean>> deliverResult) {
        if (CollectionUtils.isEmpty(deliverResult)) {
            return false;
        }
        return deliverResult.stream().allMatch(Pair::getRight);
    }

    @Override
    public void setTaskInstance(TaskInstanceDTO taskInstance) {
        this.taskInstance = taskInstance;
    }

    @Override
    public TaskInstanceDTO getTaskInstance() {
        return taskInstance;
    }
}
