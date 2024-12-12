package growth.tech.core.core.processor;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/29 14:54
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Sets;
import lombok.SneakyThrows;

import javax.annotation.processing.Filer;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.Set;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 文件读写
 *
 * @author lijiawei
 * @version ResourcesAccessor.java, v 0.1 2023年10月13日 10:53 AM lijiawei
 */
public class ResourcesAccessor {

    /**
     * 写入文件
     *
     * @param filer 文件维护
     * @throws IOException 异常
     */
    public static <T> void write(Filer filer, String resourcePath, Set<T> resourceSet) throws IOException {
        FileObject fileObject = filer.createResource(StandardLocation.CLASS_OUTPUT, "", resourcePath);

        try (OutputStream out = fileObject.openOutputStream()) {
            String config = JSON.toJSONString(resourceSet, SerializerFeature.PrettyFormat);
            out.write(config.getBytes(UTF_8));
        }
    }

    /**
     * 读取文件，注意可能有多个jar里有，所以结果是多份的
     *
     * @param classLoader 类加载器
     * @return 加载的接口
     */
    @SneakyThrows
    public static <T> Set<T> load(ClassLoader classLoader, String resourceName, TypeReference<Set<T>> typeReference) {
        Set<T> result = Sets.newHashSet();
        Enumeration<URL> enumeration = classLoader.getResources(resourceName);
        while (enumeration.hasMoreElements()) {
            URL url = enumeration.nextElement();
            try (InputStream inputStream = url.openStream()) {
                Set<T> resourceSet = JSON.parseObject(inputStream, UTF_8, typeReference.getType());
                result.addAll(resourceSet);
            }
        }
        return result;
    }

    /**
     * 通过单文件加载
     *
     * @param file 文件
     * @return 结果
     */
    @SneakyThrows
    public static <T> Set<T> load(File file, TypeReference<Set<T>> typeReference) {
        try (InputStream reader = Files.newInputStream(file.toPath())) {
            return JSON.parseObject(reader, UTF_8, typeReference.getType());
        }
    }

}
