package growth.tech.core.core.annotation.scan;

import java.lang.annotation.*;

/**
 * 编译器扫描 元注解
 * 解释：
 * 这个注解打在另一个用户注解上，用户注解再打到具体的元素上，元素会被scanner处理，入参是打了注解的Element(类或者字段方法等)
 * scanner返回的结果，会被合并成一个set(去重)以后，写入resourceFile
 * @author brain
 * @version 1.0
 * @date 2024/10/29 14:37
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface CompileScanMeta {

    /**
     * 资源文件，必须是META-INF目录下的
     * <p>
     * 例如META-INF/growth/interfaces.json
     * <p>
     * 可以把多个scan的结果放到相同的资源文件下，前提是结果类型是一样的
     * @return
     */
    String resourceFile();

    /**
     * scanner 需要继承CompileScanner，有无参构造
     * <p>
     * 1. 这里写类名而不是类因为processor处理有可能有顺序问题(可能类还不存在)
     * 2. 如果和别的scan的resourceFile一致，那么会合并到一起
     * @return
     */
    String scanner() default "com.xiaohongshu.growth.tech.core.processor.TypeStringScanner";
}
