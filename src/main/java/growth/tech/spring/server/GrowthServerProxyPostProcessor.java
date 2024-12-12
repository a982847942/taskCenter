package growth.tech.spring.server;

import growth.tech.spring.common.AbstractGrowthProxyPostProcessor;
import growth.tech.spring.properties.LogItem;

import java.lang.reflect.InvocationHandler;
import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/30 10:33
 */
/**
 * 对外服务公共逻辑处理
 */
public class GrowthServerProxyPostProcessor extends AbstractGrowthProxyPostProcessor {

    /**
     * 构造并初始化
     */
    public GrowthServerProxyPostProcessor(List<LogItem> logItemList) {
        super(logItemList);
    }

    @Override
    protected InvocationHandler buildInvocationHandler(Object bean, Class<?> interfaceClass, LogItem logItem) {
        return new GrowthServerMethodInterceptor(bean, interfaceClass, logItem);
    }

}
