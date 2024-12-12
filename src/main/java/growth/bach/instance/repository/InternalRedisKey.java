package growth.bach.instance.repository;

import edu.util.lock.distribution.redis.RedisKeyGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 内部使用的rediskey
 * @author brain
 * @version 1.0
 * @date 2024/11/1 15:38
 */
@Getter
@AllArgsConstructor
public enum InternalRedisKey implements RedisKeyGenerator {
    EVENT_ROUGH_FILTER("event", "source_type_uid", "cnts")
    ;
    private final String domain;
    private final String keyMeaning;
    private final String valueMeaning;

    @Override
    public String getBiz() {
        return "bach";
    }
}

