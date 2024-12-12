package growth.tech.spring.client;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/30 10:48
 */

import edu.common.exception.AssertUtils;
import edu.common.exception.CommonException;
import edu.common.exception.CommonResultCodeEnum;
import growth.tech.Result;
import growth.tech.spring.common.AbstractGrowthProxyPostProcessor;
import growth.tech.spring.properties.LogItem;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.util.Arrays;
import java.util.List;

/**
 * 客户端工具类
 */
/**
 * 调下游公共逻辑处理
 */
@Slf4j
public class GrowthClientProxyPostProcessor extends AbstractGrowthProxyPostProcessor {

    /**
     * 构造并初始化
     */
    public GrowthClientProxyPostProcessor(List<LogItem> logItemList) {
        super(logItemList);
    }

    @Override
    protected InvocationHandler buildInvocationHandler(Object bean, Class<?> interfaceClass, LogItem logItem) {
        return new GrowthClientMethodInterceptor(bean, interfaceClass, logItem);
    }

}
