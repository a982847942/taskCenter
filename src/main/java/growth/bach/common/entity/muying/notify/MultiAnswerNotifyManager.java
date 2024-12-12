package growth.bach.common.entity.muying.notify;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 12:24
 */

import com.alibaba.fastjson.JSONObject;
import edu.util.lock.distribution.redis.RedisKeyGenerator;
import growth.bach.manager.NotificationManager;
import growth.bach.manager.model.NotifyType;
import lombok.Data;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 多人答题通知管理员
 *
 * @author yuzhun
 * @created 2024-07-15 14:05
 */
@Component
public class MultiAnswerNotifyManager {
    @Resource
    private Jedis smallDataRedis;

    @Resource
    private NotificationManager notificationManager;

    /**
     * 汇报组队答题发奖结果
     * 当最后一个队员的结果到来后，对所有队员发送结果消息
     * 缺陷：如果最后一个人的消息丢了，全组的通知都不会发
     *
     * @param result
     */
    public void deliverPrizeReport(DeliverResult result) {
        String key = RedisKey.K.generate(result.teamId);
        storeResult(key, result);

        Set<String> userAndScores = smallDataRedis.smembers(key);
        Set<String> members = userAndScores.stream().map(it -> it.split(":")[0]).collect(Collectors.toSet());
        if (members.containsAll(result.teamMembers)) {
            smallDataRedis.del(key);
            notifyAllMembers(userAndScores, members, result);
        }
    }

    private void storeResult(String key, DeliverResult result) {
        try (Pipeline p = smallDataRedis.pipelined()) {
            p.sadd(key, String.format("%s:%s", result.userId, result.quantity));
            p.expire(key, (int) TimeUnit.MINUTES.toSeconds(60));
            p.sync();
        }
    }

    private void notifyAllMembers(Set<String> userAndScores, Set<String> members, DeliverResult result) {
        HashMap<String, String> data = new HashMap<>();
        data.put("teamId", result.teamId);
        data.put("activityId", result.activityId);
        data.put("satisfy", String.valueOf(result.satisfy));
        data.put("averageScore", String.valueOf(result.averageScore));
        data.put("scores", JSONObject.toJSONString(result.scores));

        for (String userAndScore : userAndScores) {
            data.put(userAndScore.split(":")[0], userAndScore.split(":")[1]);
        }

        for (String userId : members) {
            notificationManager.notifyAsync(NotifyType.PRIZE_DELIVER, userId, result.activityId, data);
        }
    }

    @Data
    public static class DeliverResult {
        private String activityId;
        private String teamId;
        private String userId;
        private Integer quantity;
        private List<String> teamMembers;
        private boolean satisfy;
        private Map<String, Double> scores;
        private Double averageScore;
    }

    private enum RedisKey implements RedisKeyGenerator {
        K();

        @Override
        public String getBiz() {
            return "bach";
        }

        @Override
        public String getDomain() {
            return "muyingdati";
        }

        @Override
        public String getKeyMeaning() {
            return "teamId";
        }

        @Override
        public String getValueMeaning() {
            return "deliveredUserSet";
        }
    }
}