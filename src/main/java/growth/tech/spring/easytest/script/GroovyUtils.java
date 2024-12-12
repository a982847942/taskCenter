package growth.tech.spring.easytest.script;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/30 11:27
 */

import com.google.common.collect.Maps;
import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.springframework.util.DigestUtils;

import java.math.BigInteger;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 加载groovy脚本。使用LinkedHashMap作为脚本缓存
 */
public class GroovyUtils {

    /** groovy class 缓存容量 */
    private static final int capacity = 100;
    /** groovy class loader */
    private static final GroovyClassLoader GROOVY_CLASS_LOADER = new GroovyClassLoader();
    /**
     * groovy class local-cache
     * key => md5(script)
     */
    private static final Map<String, Class<?>> CLASS_CACHE;
    static {
        // LRU
        CLASS_CACHE = Collections.synchronizedMap(new LinkedHashMap<String, Class<?>>() {
            /** 序列号 */
            private static final long serialVersionUID = -8428489665179765219L;
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > capacity;
            }
        });
    }

    /**
     * 加载并执行groovy脚本
     *
     * @param scriptText groovy脚本
     * @return 脚本运行结果
     */
    public static Object eval(String scriptText) {
        return eval(scriptText, Maps.newHashMap());
    }

    /**
     * 加载并执行groovy脚本
     *
     * @param scriptText groovy脚本
     * @param context    参数
     * @return 脚本运行结果
     */
    @SuppressWarnings("DuplicatedCode")
    public static Object eval(String scriptText, Map<String, Object> context) {
        // 1. get compiled class
        Class<?> compiledClass = getCompiledClass(scriptText);

        // 2. set binding
        Binding binding = new Binding();
        if (context != null) {
            context.forEach(binding::setVariable);
        }

        // 3. eval
        return InvokerHelper.createScript(compiledClass, binding).run();
    }

    /**
     * 获取groovy编译后生成的Java Class。
     *
     * @param scriptText groovy脚本
     * @return Java Class
     */
    public static Class<?> getCompiledClass(String scriptText) {
        // md5
        String md5Str = new BigInteger(1, DigestUtils.md5Digest(scriptText.getBytes())).toString(16);

        Class<?> clazz = CLASS_CACHE.get(md5Str);
        if (clazz == null) {
            // compile class at run time
            clazz = GROOVY_CLASS_LOADER.parseClass(scriptText);
            CLASS_CACHE.putIfAbsent(md5Str, clazz);
        }

        return clazz;
    }

}
