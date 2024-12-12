package growth.bach.sharecode.repository;

import edu.util.lock.distribution.redis.RedisKeyGenerator;
import lombok.Getter;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 13:36
 */
@Getter
public enum InternalRedisKey implements RedisKeyGenerator {
    ASSIST_INVITE_EXPIRE_SHARE_CODE("invite_assist", "share_code", "expire_share_code");

    private final String domain;
    private final String keyMeaning;
    private final String valueMeaning;

    InternalRedisKey(String domain, String keyMeaning, String valueMeaning) {
        this.domain = domain;
        this.keyMeaning = keyMeaning;
        this.valueMeaning = valueMeaning;
    }

    @Override
    public String getBiz() {
        return "bach";
    }
}
