package growth.tech.spring.lock;

import edu.common.constants.CommonConstants;
import edu.common.exception.AssertUtils;
import edu.common.exception.CommonResultCodeEnum;
import growth.tech.spring.utils.SpELUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.lang.reflect.Method;

import static growth.tech.spring.constants.AspectOrderConstants.REDIS_LOCK_ASPECT_ORDER;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/29 20:28
 */

/**
 * 基于Jedis的分布式锁切面
 */
@Slf4j
@Aspect
@Order(REDIS_LOCK_ASPECT_ORDER)
public class RedisLockAspect {

    /** redis分布式锁的前缀 */
    private static final String LOCK_REDIS_KEY_PREFIX = "string:growth.lock";

    /**
     * 构造切面bean
     */
    public RedisLockAspect(String appName, Jedis jedis) {
        this.appName = appName;
        this.jedis = jedis;
    }

    /** redis 命令执行成功返回值 */
    private static final String OK = "OK";
    /** redis 分布式锁value */
    private static final String LOCK_VAL = "1";

    /** 应用名 */
    private final String appName;

    /** jedis 客户端 */
    private final Jedis jedis;

    /** 切入点为打了{@link RedisLock}注解的方法 */
    @Pointcut(value = "@annotation(redisLock)", argNames = "redisLock")
    public void redisLockPointcut(RedisLock redisLock) {
    }

    @Around(value = "redisLockPointcut(redisLock)", argNames = "pjp, redisLock")
    public Object proceed(ProceedingJoinPoint pjp, RedisLock redisLock) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();

        // 获取redis锁key
        String param = SpELUtils.parseElExpression(redisLock.value(), String.class, pjp.getArgs(), method);
        AssertUtils.isNotBlank(param, CommonResultCodeEnum.LOCK_FAIL,
                "@RedisLock value is not valid, please check. value:" + redisLock.value());
        String redisKey = String.join(CommonConstants.POINT, LOCK_REDIS_KEY_PREFIX, appName, redisLock.group(), param);
        log.info("RedisLockAspect process... redisKey is:[{}]", redisKey);

        try {
            // todo 目前分布式锁的实现逻辑写的比较low，例如可能存在释放掉非本线程所加的锁的问题。后面有时间可以优化一下。
            boolean getLock = tryLock(redisKey, redisLock.lockTime());
            AssertUtils.isTrue(getLock, CommonResultCodeEnum.LOCK_FAIL, redisLock.lockFailedMsg());

            return pjp.proceed();
        } finally {
            boolean releaseSuccess = releaseLock(redisKey);
            if (!releaseSuccess) {
                log.warn("RedisLockAspect releaseLock fail, redisKey is:[{}]", redisKey);
            }
        }
    }

    /**
     * 尝试获取redis分布式锁
     *
     * @param redisKey    redis锁key
     * @param lockSeconds 持有锁的时间，s
     * @return 加锁是否成功
     */
    private boolean tryLock(String redisKey, long lockSeconds) {
        SetParams params = new SetParams().nx().px(lockSeconds);
        return OK.equals(jedis.set(redisKey, LOCK_VAL, params));
    }

    /**
     * 释放redis分布式锁
     *
     * @param redisKey redis锁key
     * @return 释放是否成功
     */
    private boolean releaseLock(String redisKey) {
        Long result = jedis.del(redisKey);
        return result != null && result > 0;
    }

}
