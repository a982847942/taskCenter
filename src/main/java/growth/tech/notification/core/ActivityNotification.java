package growth.tech.notification.core;

import growth.tech.notification.model.ActivityNotificationDTO;
import growth.tech.notification.utils.ActivityNotificationUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动通知
 * @author brain
 * @version 1.0
 * @date 2024/10/29 19:51
 */
public interface ActivityNotification {
    /**
     * 查询通知间隔，单位s
     * @return
     */
    int getQueryInterval();

    /**
     * 通知Id
     * @return
     */
    String getNotificationId();

    /**
     * 所属活动Id
     * @return
     */
    String getActivityId();

    /**
     * 分组
     * @return
     */
    default String getGroup(){
        return ActivityNotificationUtils.DEFAULT_GROUP;
    }

    /**
     * 可以定制额外的通知数据
     * @return
     */
    default List<ActivityNotificationDTO> queryNotification(){
        return new ArrayList<>();
    }
}
