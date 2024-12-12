package growth.tech.spring.easytest.script;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/30 11:27
 */

import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * groovy脚本加载 for Spring
 */
public class GroovySpringFactory implements ApplicationContextAware {

    /** applicationContext */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        GroovySpringFactory.applicationContext = applicationContext;
    }

    /**
     * spring环境加载并执行groovy脚本。即支持groovy脚本中使用{@link Resource}、{@link Autowired}、{@link Qualifier}注入其他bean
     */
    @Nonnull
    @SneakyThrows
    public static Object loadNewInstance(String scriptText) {
        Class<?> groovyClass = GroovyUtils.getCompiledClass(scriptText);
        Object instance = groovyClass.newInstance();

        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            Object fieldBean = null;

            // with bean-id, bean could be found by both @Resource and @Autowired,
            // or bean could only be found by @Autowired
            if (AnnotationUtils.getAnnotation(field, Resource.class) != null) {
                Resource resource = AnnotationUtils.getAnnotation(field, Resource.class);
                try {
                    if (resource != null) {
                        if (StringUtils.isNotEmpty(resource.name())) {
                            fieldBean = applicationContext.getBean(resource.name());
                        } else {
                            fieldBean = applicationContext.getBean(field.getName());
                        }
                    }
                } catch (Throwable e) {
                    // ignore
                }
                if (fieldBean == null) {
                    fieldBean = applicationContext.getBean(field.getType());
                }
            } else if (AnnotationUtils.getAnnotation(field, Autowired.class) != null) {
                Qualifier qualifier = AnnotationUtils.getAnnotation(field, Qualifier.class);
                if (qualifier != null && StringUtils.isNotEmpty(qualifier.value())) {
                    if (StringUtils.isNotEmpty(qualifier.value())) {
                        fieldBean = applicationContext.getBean(qualifier.value());
                    } else {
                        fieldBean = applicationContext.getBean(field.getType());
                    }
                }
            }

            // set spring bean
            if (fieldBean != null) {
                field.setAccessible(true);
                field.set(instance, fieldBean);
            }
        }

        return instance;
    }

}
