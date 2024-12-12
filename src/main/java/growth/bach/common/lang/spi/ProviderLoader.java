package growth.bach.common.lang.spi;

import lombok.SneakyThrows;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 提供者加载器
 * 非常重，强烈建议只在启动时使用
 * @author brain
 * @version 1.0
 * @date 2024/11/1 10:49
 */
public class ProviderLoader<T> {
    @SneakyThrows
    public List<Provider<? extends T>> load(Class<? extends T> type) {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AssignableTypeFilter(Provider.class));
        Set<BeanDefinition> beanDefs = provider.findCandidateComponents("com.xiaohongshu.growth.bach");
        List<Provider<? extends T>> result = new ArrayList<>();
        for (BeanDefinition bd : beanDefs) {
            Provider p = (Provider) Class.forName(bd.getBeanClassName()).getDeclaredConstructor().newInstance();
            if (type.isAssignableFrom(p.type())){
                result.add(p);
            }
        }
        return result;
    }
}
