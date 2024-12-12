package growth.tech.notification.model;

import lombok.Data;

import java.util.Map;

/**
 * 活动通知信息模型
 * @author brain
 * @version 1.0
 * @date 2024/10/29 19:53
 */
@Data
public class ActivityNotificationDTO {
    /**
     * 通知Id
     */
    private String notificationId;
    /**
     * 通知数据
     */
    private Map<String, String> notificationData;
}
