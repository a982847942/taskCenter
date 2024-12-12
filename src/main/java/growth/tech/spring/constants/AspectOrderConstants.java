package growth.tech.spring.constants;

/**
 * spring aspect顺序 值越小 优先级越高
 * {@link org.springframework.core.Ordered#LOWEST_PRECEDENCE}
 * {@link org.springframework.core.Ordered#HIGHEST_PRECEDENCE}
 * @author brain
 * @version 1.0
 * @date 2024/10/29 21:48
 */
public class AspectOrderConstants {
    /**
     * Redis 分布式锁
     */
    public static final int REDIS_LOCK_ASPECT_ORDER = -1;

    /**
     * client 切面
     */
    public static final int CLIENT_ASPECT_ORDER = 5;

    /**
     * rpc切面
     */
    public static final int RPC_ASPECT_ORDER = 10;
}
