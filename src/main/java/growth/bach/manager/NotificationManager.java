package growth.bach.manager;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 11:30
 */

import growth.bach.manager.model.NotifyType;
import growth.tech.notification.core.ActivityFissionAssistantEventProducer;
import growth.tech.notification.core.ActivityNotificationCenter;
import growth.tech.notification.model.ActivityFissionAssistantEventDTO;
import growth.tech.notification.model.ActivityNotificationDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 前后端通知管理者
 *
 * @author yuzhun
 * @created 2024-07-10 16:52
 */
@Slf4j
@Component
public class NotificationManager {

    @Resource
    private ActivityNotificationCenter activityNotificationCenter;

    @Resource
    private ActivityFissionAssistantEventProducer producer;

    /**
     * 异步通知前端一个消息
     *
     * @param type       消息类型
     * @param userId     目标用户
     * @param activityId 活动ID
     * @param data       消息数据
     */
    public void notifyAsync(NotifyType type, String userId, String activityId, Map<String, String> data) {
        ActivityNotificationDTO dto = getActivityNotificationDTO(type, userId, activityId, data);
        activityNotificationCenter.publishNotification(activityId, dto, userId);
    }

    private ActivityNotificationDTO getActivityNotificationDTO(NotifyType type, String userId, String activityId, Map<String, String> data) {
        ActivityNotificationDTO dto = new ActivityNotificationDTO();
        dto.setNotificationId(type.name());
        dto.setNotificationData(data);

        log.info("发送消息给前端，type: {}, activityId:{}, userId: {}, data: {}", type, activityId, userId, data);
        return dto;
    }

    public void notifyGroupAsync(NotifyType type, String userId, String activityId, Map<String, String> data, String group) {
        ActivityNotificationDTO dto = getActivityNotificationDTO(type, userId, activityId, data);
        activityNotificationCenter.publishNotification(activityId, group, dto, userId);
    }

    /**
     * 使用此方法一定要确定group和notificationId是唯一的，在业务层get后会把redis中的数据消耗掉，需要重新添加
     * @param activityId: 活动id
     * @param group: group
     * @param userId: 用户id
     * @param notificationId: 通知消息id
     * @return List<ActivityNotificationDTO>
     */
    public ActivityNotificationDTO getNotifyMsg(String activityId, String group, String userId, String notificationId) {
        List<ActivityNotificationDTO> anDTO =  activityNotificationCenter.getUserNotifications(activityId, group, userId);
        return anDTO.stream()
                .filter(it -> StringUtils.equals(it.getNotificationId(), notificationId))
                .findFirst()
                .orElse(null);
    }

    /**
     * 发送活动绑码消息至消息队列
     * @param activityId 活动id
     * @param inviterId 邀请者id
     * @param helperId 助力者id
     * @param deviceId 助力者设备id
     * @param userType 用户类型
     */
    public void sendActivityFissionTopicMsg(String activityId, String inviterId, String helperId, String deviceId, String userType) {
        ActivityFissionAssistantEventDTO msg = new ActivityFissionAssistantEventDTO(
                activityId,
                inviterId,
                helperId,
                deviceId,
                System.currentTimeMillis());

        msg.setUserType(userType);
        msg.setEventFrom("bach");
        producer.produceEvent(msg);
    }
}
