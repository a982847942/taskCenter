package growth.bach.common.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import growth.bach.common.holder.ApplicationContextHolder;
import growth.bach.common.lang.spi.Identity;
import growth.bach.instance.TaskInstanceDomainService;
import growth.bach.instance.dto.TaskInstanceDTO;
import growth.bach.instance.engine.event.UniqEvent;
import growth.bach.instance.engine.event.content.InviteAssist;
import growth.bach.instance.engine.event.reg.TriggerCondition;
import growth.bach.instance.engine.progress.ProgressContext;
import growth.bach.instance.engine.task.entity.TaskEntity;
import growth.bach.instance.engine.task.meta.BaseSuccessCondition;
import growth.bach.instance.engine.task.meta.condition.success.DefaultSuccessCondition;
import growth.bach.instance.entity.TaskBenefitDO;
import growth.bach.instance.enums.TaskInstanceStatus;
import growth.bach.instance.repository.TaskBenefitRepository;
import growth.bach.manager.NotificationManager;
import growth.bach.manager.model.NotifyType;
import growth.bach.startegy.BaseStrategy;
import growth.bach.startegy.muying.MuYingStrategy;
import growth.tech.notification.model.ActivityNotificationDTO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 邀请助力任务
 * 基础版：无限助力版
 * @author brain
 * @version 1.0
 * @date 2024/11/2 10:25
 */
@Slf4j
@Identity("INVITE_ASSISTANCE")
public class InviteAssistTask implements TaskEntity {
    @Getter
    private TaskInstanceDTO taskInstanceDTO;
    private static final String SHARE_CODE = "shareCode";

    /**
     * 获取事件触发条件
     * @return
     */
    @Override
    public TriggerCondition getTriggerCondition() {
        return taskInstanceDTO.getTaskMeta().getTaskPromoteCondition().getTriggerCondition();
    }

    @Override
    public double goForward(ProgressContext context) {
        UniqEvent event = context.getEvent();
        InviteAssist via = (InviteAssist) event.getContent();
        BaseStrategy.InviteStrategyRes res = ApplicationContextHolder.getApplicationContext().getBean(MuYingStrategy.class).satifyAssistCondition(via);
        if (!deliverPrize(res.getDecay())){
            // 错误日志
            return 0.0;
        }
        sendInviterNotification(via, res);
        return 0.0;
    }

    @Override
    public boolean isFinished() {
        TaskInstanceDTO dto = ApplicationContextHolder.getApplicationContext().getBean(TaskInstanceDomainService.class).queryInstanceId(taskInstanceDTO.getId(), taskInstanceDTO.getUserId());
        if (dto == null){
            return false;
        }
        return TaskInstanceStatus.FINISHED.equals(dto.getInstanceStatus());
    }

    /**
     * 是否成功
     * @return
     */
    @Override
    public boolean deliverPrize() {
        return false;
    }

    @Override
    public void setTaskInstance(TaskInstanceDTO taskInstance) {
        //存在码不处理
        //如果码不存在添加补充发码逻辑
        //更新DB,把码数据更新到extra中
        if (Objects.isNull(taskInstance.getExtra()) || !taskInstance.getExtra().containsKey(SHARE_CODE)){
            updateExtra(taskInstance);
        }
        this.taskInstanceDTO = taskInstance;
    }

    @Override
    public TaskInstanceDTO getTaskInstance() {
        return taskInstanceDTO;
    }

    private void sendInviterNotification(InviteAssist iva, BaseStrategy.InviteStrategyRes res) {
        String aid = taskInstanceDTO.getTaskMeta().getActivityId();
        String uid = taskInstanceDTO.getUserId();
        String groupName = String.format("invite_assist_%s", aid);

        ActivityNotificationDTO currentData = ApplicationContextHolder.getApplicationContext()
                .getBean(NotificationManager.class)
                .getNotifyMsg(aid, groupName, uid, NotifyType.INVITE_ASSIST_SUCCESS.name());

        Map<String, String> outData = new HashMap<>();
        UserInfoDTO ud = ApplicationContextHolder.getApplicationContext().getBean(RedUserServiceManager.class).getUserInfo(iva.getAssistUserId());
        if (Objects.isNull(currentData) ||
                currentData.getNotificationData().isEmpty() ||
                currentData.getNotificationData().get(groupName).isEmpty()) {
            JSONArray jArray = new JSONArray();
            buildSendInviterData(ud, jArray, res.getQuality());
            outData.put(groupName, jArray.toJSONString());
        } else {
            String inData = currentData.getNotificationData().get(groupName);
            JSONArray jArray = JSONArray.parseArray(inData);
            buildSendInviterData(ud, jArray, res.getQuality());
            outData.put(groupName, jArray.toJSONString());
        }

        ApplicationContextHolder.getApplicationContext()
                .getBean(NotificationManager.class)
                .notifyGroupAsync(NotifyType.INVITE_ASSIST_SUCCESS, uid, aid, outData, groupName);
    }

    private void buildSendInviterData(UserInfoDTO ud, JSONArray jArray, Long quality) {
        JSONObject js = new JSONObject();
        js.put("taskType", taskInstanceDTO.getTaskMeta().getTaskType().name());
        js.put("userName", ApplicationContextHolder.get().getBean(ShareCodeService.class).userNameCap(ud.getNickName()));
        js.put("avatarUrl", ud.getAvatarUrl());
        js.put("quality", String.valueOf(quality));
        jArray.add(js);
    }

    public boolean deliverPrize(double decay) {
        BaseSuccessCondition successCondition = getTaskInstanceDTO().getTaskMeta().getTaskSuccessCondition();
        if (successCondition instanceof DefaultSuccessCondition){
            Integer quantity = ((DefaultSuccessCondition) successCondition).getPrizeQuantity();
            List<TaskBenefitDO> taskBenefits = ApplicationContextHolder.getApplicationContext().getBean(TaskBenefitRepository.class).batchQuery(getTaskInstanceDTO().getTaskMeta().getId());
            List<Pair<Long, Boolean>> lpRes = taskBenefits.stream().map(tb -> deliver(decay, tb, quantity)).collect(Collectors.toList());
            if (lpRes.stream().anyMatch(p -> p.getRight().equals(Boolean.FALSE))){
                // 错误日志
                return false;
            }
        }else {
            // 错误日志 不支持的成功条件类型 异常
        }
        return true;
    }

    private Pair<Long, Boolean> deliver(double decay, TaskBenefitDO tb, Integer quantity) {
        // 构建发奖请求，调用chopin发奖服务
        return new ImmutablePair<>(tb.getBenefitId(), true);
    }
}
