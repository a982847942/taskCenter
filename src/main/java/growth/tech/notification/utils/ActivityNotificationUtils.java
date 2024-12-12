package growth.tech.notification.utils;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/29 19:54
 */
public class ActivityNotificationUtils {
    /**
     * 默认分组
     */
    public static final String DEFAULT_GROUP = "COMMON";

    /**
     * redis key模板
     */
    public static final  String ACTIVITY_NOTIFICATION_QUEUE = "growth.tech.activity_notification_queue:%s_%s_%s";

    /**
     * 生成活动通知redis key
     * @param activityId
     * @param scope
     * @param userId
     * @return
     */
    public static String getActivityNotificationRedisKey(String activityId,String scope, String userId){
        return String.format(ACTIVITY_NOTIFICATION_QUEUE, activityId, scope, userId);
    }

    /**
     * 获取通知Id
     */
    public static String getNotificationMapKey(String activityId, String group){
        return String.format("%s-%s", activityId, group);
    }
}
