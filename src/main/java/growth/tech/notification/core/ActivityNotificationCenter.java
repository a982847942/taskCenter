package growth.tech.notification.core;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/29 19:51
 */

import com.alibaba.fastjson.JSON;
import growth.tech.notification.model.ActivityNotificationDTO;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static growth.tech.notification.utils.ActivityNotificationUtils.*;

/**
 * 活动通知中心
 * <p>
 */
@Slf4j
@Setter
public class ActivityNotificationCenter {

    /** jedis */
    private Jedis jedis;

    /** 每个用户redis存储的通知数量上限 */
    private int maxQueueSize = 100;

    /** 每次获取通知时，从redis捞取的数量 */
    private int loadSizeEachTime = 20;

    /** 下次查询在多久之后 */
    private int defaultNextQueryAfter = 100000;

    /** 最后一次写入通知后，过多久过期。默认10天 */
    private int notificationExpireSeconds = 60 * 60 * 24 * 10;

    /**
     * 通知map
     * key => 通知id
     * value => List<通知bean>
     */
    private static final Map<String, List<ActivityNotification>> notificationMap = new HashMap<>();

    /**
     * 注册通知bean
     */
    public void registerNotification(String activityId, String group, ActivityNotification notification) {
        String mapKey = getNotificationMapKey(activityId, group);
        synchronized (this) {
            notificationMap.computeIfAbsent(mapKey, k -> new ArrayList<>());
        }
        notificationMap.get(mapKey).add(notification);
    }

    /**
     * 发布通知。使用默认分组
     *
     * @param activityId   活动id
     * @param notification 通知数据
     * @param targetUserId 通知目标用户
     */
    public void publishNotification(String activityId, ActivityNotificationDTO notification, String targetUserId) {
        publishNotification(activityId, DEFAULT_GROUP, notification, targetUserId);
    }

    /**
     * 发布通知
     *
     * @param activityId   活动id
     * @param group        分组
     * @param notification 通知数据
     * @param targetUserId 通知目标用户
     */
    public void publishNotification(String activityId, String group, ActivityNotificationDTO notification,
                                    String targetUserId) {
        String redisKey = getActivityNotificationRedisKey(activityId, group, targetUserId);

        long queueSize = 0;
        try (Pipeline pipeline = jedis.pipelined()) {
            pipeline.lpush(redisKey, JSON.toJSONString(notification));
            pipeline.expire(redisKey, notificationExpireSeconds);
            // 每次发布新通知同时获取redis里保存的队列长度
            Response<Long> lenResponse = pipeline.llen(redisKey);
            pipeline.sync();

            if (lenResponse != null && lenResponse.get() != null) {
                queueSize = lenResponse.get();
            }
        }

        // 如果长度大于限制，会把较早的删掉
        if (queueSize > maxQueueSize) {
            jedis.ltrim(redisKey, 0, maxQueueSize - 1);
        }
    }
    /**
     * 获取通知数据，使用默认分组
     *
     * @param activityId 活动id
     * @param userId     userId
     * @return 通知数据
     */
    public List<ActivityNotificationDTO> getUserNotifications(String activityId, String userId) {
        return getUserNotifications(activityId, DEFAULT_GROUP, userId);
    }

    /**
     * 获取通知数据
     *
     * @param activityId 活动id
     * @param group      分组
     * @param userId     userId
     * @return 通知数据
     */
    public List<ActivityNotificationDTO> getUserNotifications(String activityId, String group, String userId) {
        String activityNotificationQueueKey = getActivityNotificationRedisKey(activityId, group, userId);
        List<ActivityNotificationDTO> userNotifications = new ArrayList<>();
        String notificationInCache;
        for (int i = 0; i < loadSizeEachTime; i++) {
            // todo for循环调redis，需要优化
            notificationInCache = jedis.rpop(activityNotificationQueueKey);
            if (notificationInCache != null) {
                try {
                    userNotifications.add(JSON.parseObject(notificationInCache, ActivityNotificationDTO.class));
                } catch (Exception e) {
                    log.error("getUserNotifications parse json error: {}", notificationInCache, e);
                }
            } else {
                break;
            }
        }
        String mapKey = getNotificationMapKey(activityId, group);
        for (ActivityNotification activityNotification : getNotificationsFromMap(mapKey)) {
            userNotifications.addAll(activityNotification.queryNotification());
        }

        return userNotifications;
    }

    /**
     * 获取查询通知时间间隔，单位秒。使用默认分组
     *
     * @param activityId 活动id
     * @return 通知时间间隔
     */
    public int getQueryInterval(String activityId) {
        return getQueryInterval(activityId, DEFAULT_GROUP);
    }

    /**
     * 获取查询通知时间间隔，单位秒
     *
     * @param activityId 活动id
     * @param group 分组
     * @return 通知时间间隔
     */
    public int getQueryInterval(String activityId, String group) {
        String activityNotificationQueueKey = getNotificationMapKey(activityId, group);
        int nextQueryAfter = defaultNextQueryAfter;
        for (ActivityNotification activityNotification : getNotificationsFromMap(activityNotificationQueueKey)) {
            if (activityNotification.getQueryInterval() < nextQueryAfter) {
                nextQueryAfter = activityNotification.getQueryInterval();
            }
        }
        return nextQueryAfter;
    }

    /**
     * 根据notificationId获取对应的bean
     */
    private List<ActivityNotification> getNotificationsFromMap(String key) {
        return notificationMap.getOrDefault(key, new ArrayList<>());
    }

}
