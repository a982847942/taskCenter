package growth.tech.core.core.processor;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/29 14:59
 */

import com.google.common.collect.Sets;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.util.CollectionUtils;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 抽取公共逻辑
 *
 * @author lijiawei
 * @version AbstractResourceProcessor.java, v 0.1 2023年10月13日 10:53 AM lijiawei
 */
public abstract class AbstractResourceProcessor<T> extends AbstractProcessor {

    /** 扫描到的绑定 */
    private final Set<T> founds = Sets.newConcurrentHashSet();

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return annotationTypes().stream().map(Class::getCanonicalName).collect(Collectors.toSet());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            if (!roundEnv.processingOver()) {
                find(roundEnv);
            } else {
                generateConfigFiles();
            }
        } catch (RuntimeException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                    "resource file " + resourcePath() + " error" + ExceptionUtils.getStackTrace(e));
        }
        return false;
    }

    /**
     * 生成配置文件
     */
    private void generateConfigFiles() {
        if (CollectionUtils.isEmpty(founds)) {
            return;
        }

        Filer filer = processingEnv.getFiler();
        try {
            ResourcesAccessor.write(filer, resourcePath(), founds);
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                    "io exception" + ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 查找interfaces
     *
     * @param roundEnv 轮次
     */
    private void find(RoundEnvironment roundEnv) {
        for (Class<? extends Annotation> annotationType : annotationTypes()) {
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(annotationType);
            for (Element e : elements) {
                founds.addAll(processElement(e, roundEnv, processingEnv));
            }
        }
    }

    /**
     * 资源注解
     *
     * @return 资源注解
     */
    protected abstract Set<Class<? extends Annotation>> annotationTypes();

    /**
     * 资源路径
     *
     * @return 资源路径
     */
    protected abstract String resourcePath();

    /**
     * 处理找到的element
     *
     * @param annotatedElement 找到的元素
     * @param roundEnv         轮次
     * @param processingEnv    处理环境
     * @return 处理结果
     */
    protected abstract Set<T> processElement(Element annotatedElement, RoundEnvironment roundEnv,
                                             ProcessingEnvironment processingEnv);

}
