package growth.bach.common.lang.spi;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/1 10:52
 */
@Slf4j
public class IdentityScanner<T> {
    @SneakyThrows
    public Map<String, Class<? extends T>> scan(Class<? extends T> type, String packagePath) {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(Identity.class));
        Set<BeanDefinition> beanDefs = provider.findCandidateComponents(packagePath);
        HashMap<String, Class<? extends T>> result = new HashMap<>();
        for (BeanDefinition bd : beanDefs) {
            if (!(bd instanceof AnnotatedBeanDefinition)) {
                continue;
            }
            Class<?> clazz = Class.forName(bd.getBeanClassName());
            if (!type.isAssignableFrom(clazz)) {
                continue;
            }
            Map<String, Object> annotAttributeMap = ((AnnotatedBeanDefinition) bd).getMetadata().getAnnotationAttributes(Identity.class.getCanonicalName());
            String id = annotAttributeMap.get("value").toString();
            if (result.containsKey(id)) {
                log.error("错误编程, 同一个类型的Condition下定义的id不能有重复的，当前类是{}，冲突的类是{}, 冲突的ID是{}", bd.getBeanClassName(), result.get(id), id);
                throw new IllegalStateException("ConditionDefinition定义的id不能有重复的");
            }
            result.put(id, (Class<? extends T>) clazz);
        }
        return result;
    }
}
