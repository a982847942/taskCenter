package growth.tech.spring.common;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/30 10:33
 */

import growth.tech.spring.properties.LogItem;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 抽取公共逻辑
 *
 * @author lijiawei
 * @date 2024/07/26 16:54 星期五
 */
@Slf4j
@Getter
public abstract class AbstractGrowthProxyPostProcessor implements BeanPostProcessor {

    /**
     * key -> Iface接口 class
     * value -> 日志配置
     */
    private final Map<Class<?>, LogItem> logConfigMap = new HashMap<>();

    /**
     * 构造并初始化
     */
    public AbstractGrowthProxyPostProcessor(List<LogItem> logItemList) {
        if (CollectionUtils.isEmpty(logItemList)) {
            log.info("logItemList is empty! skip...");
            return;
        }

        // 解析配置参数
        for (LogItem logItem : logItemList) {
            if (StringUtils.isEmpty(logItem.getInterfaceName())) {
                log.error("interfaceName is empty!");
                continue;
            }
            try {
                logConfigMap.put(Class.forName(logItem.getInterfaceName()), logItem);
            } catch (Throwable e) {
                log.error("className parse error, logItem:{}", logItem, e);
            }
        }
    }

    @Override
    public Object postProcessAfterInitialization(@Nonnull Object bean, @Nonnull String beanName) throws BeansException {
        if (logConfigMap.isEmpty()) {
            return bean;
        }
        Class<?> beanClass = bean.getClass();
        Class<?>[] interfaces = beanClass.getInterfaces();

        for (Class<?> clientInterface : logConfigMap.keySet()) {
            for (Class<?> inter : interfaces) {
                if (clientInterface.isAssignableFrom(inter)) {
                    LogItem logItem = logConfigMap.getOrDefault(clientInterface, new LogItem());
                    return Proxy.newProxyInstance(
                            beanClass.getClassLoader(),
                            beanClass.getInterfaces(),
                            buildInvocationHandler(bean, inter, logItem));
                }
            }
        }
        return bean;
    }

    /**
     * 构造代理处理逻辑
     *
     * @param bean  bean
     * @param interfaceClass 代理的接口
     * @param logItem 日志参数
     * @return 代理对象处理逻辑
     */
    protected abstract InvocationHandler buildInvocationHandler(Object bean, Class<?> interfaceClass, LogItem logItem);

}
