package growth.tech.spring.client;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/30 10:47
 */

import growth.tech.spring.common.AbstractLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static growth.tech.spring.constants.AspectOrderConstants.CLIENT_ASPECT_ORDER;

/**
 * RPC客户端切面，统一打印调下游的出入参数、做监控等
 */
@Slf4j
@Aspect
@Component
@Order(CLIENT_ASPECT_ORDER)
public class GrowthClientAspect extends AbstractLogAspect {

    /**
     * 统一处理RPC异常
     *
     * @param pjp 切入点参数
     * @return 方法返回结果
     * @throws Throwable Throwable
     */
    @Around("@annotation(com.xiaohongshu.growth.tech.spring.client.GrowthClient) " +
            "|| @within(com.xiaohongshu.growth.tech.spring.client.GrowthClient)")
    public Object proceed(ProceedingJoinPoint pjp) throws Throwable {
        GrowthClient annotation = getAnnotation(pjp, GrowthClient.class);
        // 理论上走到这里，annotation一定不为空
        if (annotation == null || !annotation.enable()) {
            return pjp.proceed();
        }
        long startTime = System.currentTimeMillis();

        try {
            Object result = pjp.proceed();
            printLog(pjp, true, startTime, annotation.logStrategy(), result, null);
            return result;
        } catch (Throwable t) {
            printLog(pjp, false, startTime, annotation.logStrategy(), null, t);
            throw t;
        }
    }

}
