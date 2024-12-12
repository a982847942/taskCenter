package growth.tech.core.core.annotation.scan;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import java.util.Set;

/**
 * 编译期注解扫描器
 * @author brain
 * @version 1.0
 * @date 2024/10/29 14:37
 */
public interface CompileScanner<T> {

    /**
     * 扫描
     * @param element 当前element
     * @param roundEnv 本轮处理情况
     * @param processingEnv 处理环境
     * @return 结果
     */
    Set<T> scan(Element element, RoundEnvironment roundEnv, ProcessingEnvironment processingEnv);
}
