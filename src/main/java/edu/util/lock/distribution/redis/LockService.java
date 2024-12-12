package edu.util.lock.distribution.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.UUID;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/26 13:47
 */
public class LockService {
    public static class Lock implements AutoCloseable {
        private LockService lockService;
        private String entityId;
        private String lockKey;
        private String token;

        @Override
        public void close() {
            lockService.releaseByThis(this);
        }

        public Lock() {
        }

        public Lock(LockService lockService, String entityId, String lockKey, String token) {
            this.lockService = lockService;
            this.entityId = entityId;
            this.lockKey = lockKey;
            this.token = token;
        }

        public LockService getLockService() {
            return lockService;
        }

        public void setLockService(LockService lockService) {
            this.lockService = lockService;
        }

        public String getEntityId() {
            return entityId;
        }

        public void setEntityId(String entityId) {
            this.entityId = entityId;
        }

        public String getLockKey() {
            return lockKey;
        }

        public void setLockKey(String lockKey) {
            this.lockKey = lockKey;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        @Override
        public String toString() {
            return "Lock{" +
                    "lockService=" + lockService +
                    ", entityId='" + entityId + '\'' +
                    ", lockKey='" + lockKey + '\'' +
                    ", token='" + token + '\'' +
                    '}';
        }
    }


    @Resource
    private Jedis smallDataJedis;

    // 重试次数
    private static final Integer DEFAULT_LOCK_RETRY_COUNT = 3;
    private static final Integer DEFAULT_LOCK_RETRY_WAIT_MILLIS = 100;

    private static final String REDIS_LOCK_KEY_TEMPLATE = "string:mozart:lock:%s:%s";

    public Lock waitLock(String entityId, String lockKey, Integer expireSeconds) throws InterruptedException {
        return waitLock(entityId, lockKey, expireSeconds, DEFAULT_LOCK_RETRY_COUNT, DEFAULT_LOCK_RETRY_WAIT_MILLIS);
    }

    private Lock waitLock(String entityId, String lockKey, Integer expireSeconds, Integer retryTimes, Integer retryMillis) throws InterruptedException {
        String token = UUID.randomUUID().toString();
        int count = 0;
        SetParams setParams = new SetParams().nx().ex(expireSeconds);
        while (!"OK".equals(smallDataJedis.set(String.format(REDIS_LOCK_KEY_TEMPLATE, entityId, lockKey), token, setParams))) {
            count++;
            if (count >= retryTimes) {
                // 异常自定义LockException
                throw new RuntimeException(String.format("Lock for %s:%s acquire failed after %d retries.", entityId, lockKey, retryTimes));
            }
            // 考虑使用指数退避算法
            Thread.sleep(retryMillis);
        }
        return new Lock(this, entityId, lockKey, token);
    }

    public void release(Lock lock) {
        lock.close();
    }

    private void releaseByThis(Lock lock) {
        smallDataJedis.eval(
                "if (redis.call('GET', KEYS[1]) == ARGV[1]) then return redis.call('DEL', KEYS[1]) else return 0 end",
                1,
                String.format(REDIS_LOCK_KEY_TEMPLATE, lock.entityId, lock.lockKey),
                lock.token
        );
    }

    public Optional<Lock> tryLock(String entityId, String lockKey, Integer expireSeconds) {
        String token = UUID.randomUUID().toString();
        SetParams setParams = new SetParams().nx().ex(expireSeconds);
        if("OK".equals(smallDataJedis.set(String.format(REDIS_LOCK_KEY_TEMPLATE, entityId, lockKey), token, setParams))) {
            return Optional.of(new Lock(this, entityId, lockKey, token));
        }
        return Optional.empty();
    }
}
