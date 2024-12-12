package growth.tech.spring.easytest.utils;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/30 11:28
 */

import growth.tech.core.common.utils.AopUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * easy test 工具类
 *
 * @author lijiawei
 * @date 2024/09/14 14:57 星期六
 */
public class EasyTestUtils {

    /**
     * 获取bean的方法列表及bean的原始对象
     *
     * @param bean spring bean
     * @return bean原始对象 & bean的方法列表
     */
    public static Pair<Object, Method[]> getTargetObjectWithMethods(Object bean) {
        if (bean == null) {
            return Pair.of(null, new Method[]{});
        }
        // 去掉spring代理
        Object beanTarget = AopUtils.getTargetObject(bean);
        if (beanTarget == null) {
            return Pair.of(null, new Method[]{});
        }

        /*
            getDeclareMethods() 获取的是该类里声明的方法，包含private等所有方法，但不包含父类继承的方法；
            getMethods() 获取该类及所有父类继承的方法，但只有public的方法；
            此处将这两个集合 合并去重返回
         */
        Method[] methods = Stream.concat(Arrays.stream(beanTarget.getClass().getMethods()),
                        Arrays.stream(beanTarget.getClass().getDeclaredMethods()))
                .distinct()
                // 观察发现每次服务重启，method顺序会变，此处指定一个排序顺序
                .sorted(Comparator.comparing(Method::getModifiers)
                        .thenComparing(Method::getName)
                        .thenComparing(Method::getParameterCount))
                .toArray(Method[]::new);
        return Pair.of(beanTarget, methods);
    }

}
