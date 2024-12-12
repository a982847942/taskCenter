package growth.tech.config.annotation;

import growth.tech.core.core.annotation.scan.CompileScanMeta;
import growth.tech.core.core.processor.ResourceConstants;

import java.lang.annotation.*;

/**
 * 定义内存项目，配置项目在应用中仅支持读取
 * @author brain
 * @version 1.0
 * @date 2024/10/29 14:34
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@CompileScanMeta(resourceFile = ResourceConstants.CONFIG_TYPES_FILE)
public @interface Config {
}
