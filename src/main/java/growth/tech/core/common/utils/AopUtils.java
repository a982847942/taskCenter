package growth.tech.core.common.utils;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/29 15:17
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.Advised;

/**
 * @author lijiawei
 * @date 2024/09/14 11:59 星期六
 */
@Slf4j
public class AopUtils extends org.springframework.aop.support.AopUtils {

    /**
     * 获取去掉spring动态代理的服务
     *
     * @param candidate 服务
     * @return 去掉动态代理的服务
     */
    @SuppressWarnings("unchecked")
    public static <T> T getTargetObject(Object candidate) {
        try {
            if (candidate == null) {
                return null;
            }
            if (isAopProxy(candidate) && candidate instanceof Advised) {
                Object target = ((Advised) candidate).getTargetSource().getTarget();
                // 多重代理，需要循环
                return getTargetObject(target);
            }
        } catch (Throwable e) {
            log.error("[getTargetError] candidate={}", candidate, e);
        }
        return (T) candidate;
    }

}
