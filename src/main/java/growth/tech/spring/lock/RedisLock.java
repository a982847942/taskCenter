package growth.tech.spring.lock;

import java.lang.annotation.*;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/29 20:28
 */
/**
 * 基于Redis的分布式锁 {@link RedisLockAspect}
 * <p>
 * 最后生成的redis key形如：string:growth.lock:{appName}:{group}:{param}
 * <ol>
 *     <li>growth.lock：为redis分布式锁的通用前缀；</li>
 *     <li>appName：区分不同应用；</li>
 *     <li>group：用于区分某个业务场景，例如createTask、finishTask等。在本注解中进行指定；</li>
 *     <li>具体参数值，一般是userId。可以使用SpEL表达式指定。</li>
 * </ol>
 *
 * <blockquote><pre>{@code
 * // step1: 先注册切面Bean，指定使用的jedis
 * // GrowthSDKConfig.java
 * @Configuration
 * public class GrowthSDKConfig {
 *     @Bean
 *     public RedisLockAspect redisLockAspect(Jedis jedis) {
 *         RedisLockAspect aspect = new RedisLockAspect();
 *         aspect.setAppName("mozart");
 *         aspect.setJedis(jedis);
 *         return aspect;
 *     }
 * }
 *
 * // step2: 代码中使用，注解的key属性是基于SpEL表达式的，参考：https://itmyhome.com/spring/expressions.html
 * // DemoService.java
 * @Service
 * public class DemoService {
 *     @RedisLock(group = "createTask", key = "#userId")
 *     public void createTask(String userId) {
 *         // doSomething...
 *     }
 *
 *     @RedisLock(group = "createTask2", key = "#request.userId")
 *     public void createTask2(Request request) {
 *         // doSomething...
 *     }
 * }
 *
 * // Request.java
 * @Data
 * public class Request {
 *     private String userId;
 * }
 * }</pre></blockquote>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisLock {

    /**
     * 锁分组名称，用于避免重复key
     * @return
     */
    String group() default "default";

    /**
     * key表达式
     * @return
     */
    String value();

    /**
     * 获取锁失败时候的提示信息
     * 加锁失败时会抛出 {@link edu.common.exception.CommonException}
     * code为{@link  edu.common.exception.CommonResultCodeEnum#LOCK_FAIL}
     * message为本属性值
     * @return
     */
    String lockFailedMsg() default "你的手速太快了，请稍后重试";

    /**
     * 最大持有锁的时间，如果超过该时间仍未主动释放，将自动释放
     * 其实就是redis key的过期时间
     * @return
     */
    long lockTime() default 3000;
}
