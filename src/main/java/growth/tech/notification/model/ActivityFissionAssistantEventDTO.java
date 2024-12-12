package growth.tech.notification.model;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/29 20:08
 */
@Data
@Accessors(chain = true)
public class ActivityFissionAssistantEventDTO {
    public ActivityFissionAssistantEventDTO(
            @NonNull String activityId,
            @NonNull String inviterUserId,
            @NonNull String helperUserId,
            @NonNull String helperDeviceId,
            long eventTime) {
        this.activityId = activityId;
        this.inviterUserId = inviterUserId;
        this.helperUserId = helperUserId;
        this.helperDeviceId = helperDeviceId;
        this.eventTime = eventTime;
    }

    private String activityId; // 活动id，必传
    private String inviterUserId; // 邀请人用户id，必传
    private String helperUserId; // 被邀请人用户id，必传
    private String helperDeviceId; // 被邀请人设备id，必传
    private long eventTime; // 助力发生时间，单位毫秒，必传
    private String userType; // 裂变侧实时判断的用户类型：NEW/RECALL/ACTIVE
    private String eventFrom; // 事件来源，来自XX系统：以服务树中的服务名为准
    // 系统特有字段：
    private String ariesOrderId; // 小红推广订单id

}
