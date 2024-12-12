package growth.tech.core.core.processor;

import growth.tech.core.core.annotation.scan.CompileScanner;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Collections;
import java.util.Set;

/**
 * 类或接口  获取类名的scanner
 *
 * @author brain
 * @version 1.0
 * @date 2024/10/29 14:49
 */
public class TypeStringScanner implements CompileScanner<String> {
    @Override
    public Set<String> scan(Element element, RoundEnvironment roundEnv, ProcessingEnvironment processingEnv) {
        if (element.getKind() == ElementKind.CLASS || element.getKind() == ElementKind.INTERFACE) {
            String binaryName = processingEnv.getElementUtils().getBinaryName((TypeElement) element).toString();
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "[TypeStringScanner] add to config:"
                    + binaryName, element);
            return Collections.singleton(binaryName);
        }
        return Collections.emptySet();
    }
}
