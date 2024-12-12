package growth.bach.instance.repository;

import com.google.common.collect.Lists;
import growth.bach.instance.entity.EventRegistrarDO;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/1 15:20
 */
@Component
public class EventTaskRegisterRepository {
    private static final String updateExpireScripts = "local key = KEYS[1] " +
            "local localExpireTime = tonumber(ARGV[1]) " +
            "local ttl = redis.call('TTL', key) " +
            "if ttl > 0 then " +
            "    if ttl < localExpireTime then " +
            "        local newExpireTime = localExpireTime - ttl + ttl " +
            "        redis.call('EXPIRE', key, newExpireTime) " +
            "    end " +
            "end " +
            "return ttl";

    @Resource
    private Jedis smallDataRedis;

//    @Resource
//    private EventTaskRegisterMapper eventTaskRegisterMapper;\
    public void createEventTaskRegister(EventRegistrarDO entity) {
        entity.setId(null);
//        eventTaskRegisterMapper.insert(entity);
        createOrUpdateFilterKey(entity);
    }

    /**
     * 获取关心事件的候选人
     * 粗筛
     * 具体命中哪个注册交由外部决定
     *
     * @param source
     * @param eventType
     * @param userId
     * @return
     */
    public List<EventRegistrarDO> getCandidates(String source, String eventType, String userId) {
        if (!existsRegister(source, eventType, userId)) {
            return Collections.emptyList();
        }

//        QueryWrapper<EventRegistrarDO> qw = new QueryWrapper<>();
//        qw.eq("source", source)
//                .eq("event_type", eventType)
//                .eq("user_id", userId)
//                .apply("expire_time > {0}", LocalDateTime.now());

//        return eventTaskRegisterMapper.selectList(qw);
        return null;
    }

    public boolean existsRegister(String source, String eventType, String userId) {
        String key = InternalRedisKey.EVENT_ROUGH_FILTER.generate(String.format("%s_%s_%s", source, eventType, userId));
        return smallDataRedis.exists(key);
    }

    private void createOrUpdateFilterKey(EventRegistrarDO entity) {
        String key = InternalRedisKey.EVENT_ROUGH_FILTER.generate(String.format("%s_%s_%s", entity.getSource(), entity.getEventType(), entity.getUserId()));
        long expireSeconds = Duration.between(LocalDateTime.now(), entity.getExpireTime()).abs().getSeconds();
        smallDataRedis.set(key, "1", new SetParams().nx().ex((int) expireSeconds));
        smallDataRedis.eval(updateExpireScripts, Lists.newArrayList(key), Lists.newArrayList(String.valueOf(expireSeconds)));
    }
}
