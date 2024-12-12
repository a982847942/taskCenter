package growth.tech.core.core.processor;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/29 15:00
 */

import growth.tech.core.core.annotation.scan.CompileScanMeta;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Set;
/**
 * 元注解处理器
 * <p>
 * 打了{@link CompileScanMeta}注解的「注解」，统一生成JSON文件到{@link ResourceConstants#COMPILE_SCAN_ANNOTATIONS_FILE}
 *
 * @author lijiawei
 * @version CompileScanMetaAnnotationProcessor.java, v 0.1 2023年11月21日 6:57 PM lijiawei
 */
public class CompileScanMetaAnnotationProcessor extends AbstractResourceProcessor<String> {

    @Override
    protected Set<Class<? extends Annotation>> annotationTypes() {
        return Collections.singleton(CompileScanMeta.class);
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    protected String resourcePath() {
        return ResourceConstants.COMPILE_SCAN_ANNOTATIONS_FILE;
    }

    @Override
    protected Set<String> processElement(Element annotatedElement, RoundEnvironment roundEnv,
                                         ProcessingEnvironment processingEnv) {
        if (annotatedElement.getKind() == ElementKind.ANNOTATION_TYPE) {
            String binaryName = processingEnv.getElementUtils().getBinaryName((TypeElement) annotatedElement)
                    .toString();
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "[CompileScanMeta]add to config:"
                    + binaryName, annotatedElement);
            return Collections.singleton(binaryName);
        }
        return Collections.emptySet();
    }
}
